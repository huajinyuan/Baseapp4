package com.example.huaxiang.network;


import com.example.huaxiang.model.BaseInfo;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.network.exception.ErrorThrowable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by caiyk on 2017/3/9.
 */
public class ComposeResponseData<T extends Response> implements Observable.Transformer<T, T> {

    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return ResponseHandle.checkToken()
                .observeOn(Schedulers.io())
                .flatMap(token -> tObservable)
                .flatMap(new ReadListDataFunc<>())
                .onErrorResumeNext(ResponseHandle.errorResumeFunc())
                .retryWhen(ResponseHandle.retryAndRefreshToken())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static class ReadListDataFunc<E extends Response> implements Func1<E, Observable<E>> {

        @Override
        public Observable<E> call(E response) {
            if (response.isSuccess()) {
                if (response.data instanceof BaseInfo) {
                    ((BaseInfo) response.data).setSuccessHintMsg(response.message);
                }
                return Observable.just(response);
            }
            return Observable.error(new ErrorThrowable(response.code, response.message));
        }
    }

}
