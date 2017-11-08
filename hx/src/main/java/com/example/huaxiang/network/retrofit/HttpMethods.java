package com.example.huaxiang.network.retrofit;

import android.util.Log;

import com.example.huaxiang.hx.m.LoginData_pt;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.network.ComposeResponseData;

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
    public ApiStores demoService;

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

//    public Observable<LoginData_pt> login(String username, String password) {
//        return demoService.doLogin_pt(username, password)
//                .map(new ServerResultFunc<LoginData_pt>())
//                .onErrorResumeNext(new HttpResultFunc<LoginData_pt>())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    public static void start(Observable observable, Subscriber subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public Observable<Response<LoginData_pt>> login(String username, String password) {
        return demoService.doLogin_pt(username, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<LoginData_pt>> login2(String username, String password) {
        return demoService.doLogin_pt(username, password)
                .compose(new ComposeResponseData<Response<LoginData_pt>>());
    }

}
