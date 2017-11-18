package com.example.zhongchou.network;

import com.example.zhongchou.base.ReturnCode;
import com.example.zhongchou.network.exception.ErrorThrowable;
import com.example.zhongchou.network.exception.NoNetworkException;
import com.example.zhongchou.utils.NetworkUtils;
import com.google.gson.JsonParseException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 */

public class ResponseHandle {
    private static IRetryListener mIRetryListener;

    public static Observable<? extends Object> checkToken() {
        return NetworkUtils.isNetworkAvailable().subscribeOn(Schedulers.io());
    }

    public static <T> Func1<Throwable, Observable<? extends T>> errorResumeFunc() {
        return throwable -> {
            if (throwable instanceof UnknownHostException || throwable instanceof JsonParseException) {
                return Observable.error(new ErrorThrowable(ReturnCode.LOCAL_UNKNOWN_ERROR, "未知错误"));
            } else if (throwable instanceof SocketTimeoutException || throwable instanceof ConnectException || throwable instanceof SocketException) {
                return Observable.error(new ErrorThrowable(ReturnCode.LOCAL_TIMEOUT_ERROR, "连接错误"));
            } else if (throwable instanceof NoNetworkException) {
                return Observable.error(new ErrorThrowable(ReturnCode.LOCAL_NO_NETWORK, "请打开网络设置"));
            } else if (throwable instanceof ErrorThrowable) {
                return Observable.error(throwable);
            }
            return Observable.error(new ErrorThrowable(ReturnCode.LOCAL_UNKNOWN_ERROR, throwable.toString()));
        };
    }


    public static Func1<Observable<? extends Throwable>, Observable<?>> retryAndRefreshToken() {
        if (mIRetryListener == null) {
            return observable -> observable;
        }
        return mIRetryListener.retry();
    }

    public static void setRetryListener(IRetryListener IRetryListener) {
        mIRetryListener = IRetryListener;
    }

    public interface IRetryListener {
        Func1<Observable<? extends Throwable>, Observable<?>> retry();
    }
}
