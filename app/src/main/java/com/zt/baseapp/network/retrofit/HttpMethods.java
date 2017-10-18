package com.zt.baseapp.network.retrofit;

import android.util.Log;

import com.zt.baseapp.model.Response;
import com.zt.baseapp.network.ComposeData;
import com.zt.baseapp.network.ComposeResponseData;
import com.zt.baseapp.network.exception.ErrorThrowable;
import com.zt.baseapp.pt.model.Logd;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/5/31.
 */
public class HttpMethods {
    public static final String BASE_URL = "http://www.baby25.cn/jeesite/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ApiStores demoService;

    //构造方法私有
    private HttpMethods() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        demoService = retrofit.create(ApiStores.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Observable<Response<Logd>> login2(String username, String password) {
        return demoService.doLogin(username, password)
                .compose(new ComposeResponseData<Response<Logd>>());

    }


    public Observable<Logd> login(String username, String password) {
        return demoService.doLogin(username, password)
                .map(new ServerResultFunc<Logd>())
                .onErrorResumeNext(new HttpResultFunc<Logd>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private class ServerResultFunc<T> implements Func1<Response<T>, T> {
        @Override
        public T call(Response<T> httpResult) {
            if (httpResult.code != 0) {
//                throw new ServerException(httpResult.code,httpResult.msg);
//                return ErrorThrowable.convertErr();
                Log.e("=================", "=========error  " + httpResult.code);

            }
            return httpResult.data;
        }
    }

    private class HttpResultFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable throwable) {
//            return Observable.error(ExceptionEngine.handleException(throwable));
            return Observable.error(throwable);
        }
    }


}
