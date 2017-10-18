package com.zt.baseapp.network;


import com.zt.baseapp.model.BaseInfo;
import com.zt.baseapp.model.Response;
import com.zt.baseapp.network.exception.ErrorThrowable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 *
 */

public class ComposeData<T> implements Observable.Transformer<Response<T>, T> {

    @Override
    public Observable<T> call(Observable<Response<T>> responseObservable) {
        return ResponseHandle.checkToken()
                .observeOn(Schedulers.io())
                .flatMap(token -> responseObservable)
                .flatMap(new ReadDataFunc<>())
                .onErrorResumeNext(ResponseHandle.errorResumeFunc())
                .retryWhen(ResponseHandle.retryAndRefreshToken())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static class ReadDataFunc<E> implements Func1<Response<E>, Observable<E>> {

        @Override
        public Observable<E> call(Response<E> response) {
            if (response.isSuccess()) {
                if (response.data instanceof BaseInfo) {
                    ((BaseInfo) response.data).setSuccessHintMsg(response.msg);
                }
                return Observable.just(response.data);
            }
            return Observable.error(new ErrorThrowable(response.code, response.msg));
        }
    }
}
