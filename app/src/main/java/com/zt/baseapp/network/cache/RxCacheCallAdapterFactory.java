package com.zt.baseapp.network.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;


public class RxCacheCallAdapterFactory extends CallAdapter.Factory {
    private final CachingSystem cachingSystem;

    public static RxCacheCallAdapterFactory create(CachingSystem cachingSystem) {
        return new RxCacheCallAdapterFactory(cachingSystem);
    }

    private RxCacheCallAdapterFactory(CachingSystem cachingSystem) {
        this.cachingSystem = cachingSystem;
    }

    @Override
    public CallAdapter<?> get(final Type returnType, final Annotation[] annotations, final Retrofit retrofit) {
        boolean isContinue = false;
        NetCache netCache = null;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(NetCache.class)) {
                netCache = (NetCache) annotation;
                isContinue = true;
            }
        }
        if (!isContinue) return null;
        Class<?> rawType = getRawType(returnType);
        if (rawType != Observable.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            String name = "Observable";
            throw new IllegalStateException(name + " return type must be parameterized"
                    + " as " + name + "<Foo> or " + name + "<? extends Foo>");
        }

        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> genRawType = getRawType(observableType);
        if (genRawType != Response.class && genRawType != Result.class) {
            return new CacheCallAdapter(observableType, cachingSystem, annotations, retrofit, netCache);
        }
        return null;
    }


    static final class CacheCallAdapter implements CallAdapter<Observable<?>> {
        private final Type mResponseType;
        private final CachingSystem mCachingSystem;
        private final Annotation[] mAnnotations;
        private final Retrofit mRetrofit;
        private final NetCache mNetCache;

        public CacheCallAdapter(Type actualType, CachingSystem cachingSystem, Annotation[] annotations, Retrofit retrofit, NetCache netCache) {
            this.mResponseType = actualType;
            this.mCachingSystem = cachingSystem;
            this.mAnnotations = annotations;
            this.mRetrofit = retrofit;
            this.mNetCache = netCache;
        }

        @Override
        public Type responseType() {
            return mResponseType;
        }

        @Override
        public <R> Observable<?> adapt(Call<R> call) {
            Observable<R> network = Observable.create(new CallOnSubscribe<>(call)) //
                    .lift(subscriber -> new Subscriber<Response<R>>(subscriber) {
                        @Override
                        public void onCompleted() {
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onNext(Response<R> rResponse) {
                            if (rResponse.isSuccessful()) {
                                subscriber.onNext(rResponse.body());
                                byte[] rawData = CacheUtils.responseToBytes(mRetrofit, rResponse.body(), responseType(), mAnnotations);
                                mCachingSystem.addInCache(rResponse, rawData);
                            } else {
                                subscriber.onError(new HttpException(rResponse));
                            }
                        }
                    });

            Observable<R> memory = Observable.create(subscriber -> {
                byte[] data = mCachingSystem.getFromCache(buildRequestFromCall(call).newBuilder().build());
                if (data != null) {
                    final R convertedData = CacheUtils.bytesToResponse(mRetrofit, mResponseType, mAnnotations, data);
                    subscriber.onNext(convertedData);
                }
                subscriber.onCompleted();
            });
            return Observable.concat(memory, network).first().subscribeOn(Schedulers.io());
        }

        private Request buildRequestFromCall(Call baseCall) {
            try {
                Field argsField = baseCall.getClass().getDeclaredField("args");
                argsField.setAccessible(true);
                Object[] args = (Object[]) argsField.get(baseCall);
                //retrofit2.0更改了字段(1.0+)requestFactory-->(2.0+)serviceMethod
                Field serviceMethodField = baseCall.getClass().getDeclaredField("serviceMethod");
                serviceMethodField.setAccessible(true);
                Object requestFactory = serviceMethodField.get(baseCall);
                //retrofit2.0更改了方法(1.0+)create-->(2.0+)toRequest
                Method createMethod = requestFactory.getClass().getDeclaredMethod("toRequest", Object[].class);
                createMethod.setAccessible(true);
                return (Request) createMethod.invoke(requestFactory, new Object[]{args});
            } catch (Exception exc) {
//                Log.e("buildRequestFromCall"+exc.toString());
                return null;
            }
        }
    }


    static final class CallOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {
        private final Call<T> originalCall;

        CallOnSubscribe(Call<T> originalCall) {
            this.originalCall = originalCall;
        }

        @Override
        public void call(final Subscriber<? super Response<T>> subscriber) {
            // Since Call is a one-shot type, clone it for each new subscriber.
            Call<T> call = originalCall.clone();

            // Wrap the call in a helper which handles both unsubscription and backpressure.
            RequestArbiter<T> requestArbiter = new RequestArbiter<>(call, subscriber);
            subscriber.add(requestArbiter);
            subscriber.setProducer(requestArbiter);
        }
    }


    static final class RequestArbiter<T> extends AtomicBoolean implements Subscription, Producer {
        private final Call<T> call;
        private final Subscriber<? super Response<T>> subscriber;

        RequestArbiter(Call<T> call, Subscriber<? super Response<T>> subscriber) {
            this.call = call;
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            if (n < 0) throw new IllegalArgumentException("n < 0: " + n);
            if (n == 0) return; // Nothing to do when requesting 0.
            if (!compareAndSet(false, true)) return; // Request was already triggered.

            try {
                Response<T> response = call.execute();
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(response);
                }
            } catch (Throwable t) {
                Exceptions.throwIfFatal(t);
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(t);
                }
                return;
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        }

        @Override
        public void unsubscribe() {
            call.cancel();
        }

        @Override
        public boolean isUnsubscribed() {
            return call.isCanceled();
        }
    }
}