package com.example.zhongchou.utils;


import com.example.zhongchou.network.exception.NoNetworkException;

import rx.Observable;

/**
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 */

public class NetworkUtils {

    private NetworkUtils() {
    }

    public static Observable<Boolean> isNetworkAvailable() {
        return Observable.just(AppContext.isNetworkAvailable())
                .flatMap(isAvailable -> isAvailable == true ?
                        Observable.just(isAvailable) : Observable.error(new NoNetworkException()));
    }


}
