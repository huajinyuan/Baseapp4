package com.example.choujiang.module.base;

/**
 * Created by caiyk on 2017/3/8.
 */

public interface IBaseView {

    void showLoadingDialog();

    void closeLoadingDialog();

    void showError(Throwable throwable);
}
