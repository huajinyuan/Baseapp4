package com.example.huaxiang.module.loading;

/**
 * Created by caiyk on 2016/12/30 17:07
 * Email:781208202@qq.com
 */

public interface LoadingViewListener {

    void showLoadingPage();

    void showLoadingRetry(int code, String errorMsg);

    void showLoadingContent();

    void showLoadingEmpty();

}
