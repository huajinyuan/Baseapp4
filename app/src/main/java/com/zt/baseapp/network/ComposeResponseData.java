package com.zt.baseapp.network;


import com.zt.baseapp.model.BaseInfo;
import com.zt.baseapp.model.Response;
import com.zt.baseapp.network.exception.ErrorThrowable;

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
                    ((BaseInfo) response.data).setSuccessHintMsg(response.msg);
                }
                return Observable.just(response);
            }
            return Observable.error(new ErrorThrowable(response.code, response.msg));
        }
    }

}
