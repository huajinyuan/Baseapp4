package com.example.choujiang.module.base;

import java.util.List;

/**
 * Created by caiyk on 2017/1/12.
 * Email:781208202@qq.com
 */

public interface IListView<D> extends IBaseView {
    void showLoadMoreState(int code, String msg);

    void showListItems(boolean refresh, List<D> dataList);

    void showLoadingPage();

    void showLoadingRetry(int code, String errorMsg);

    void showLoadingContent();

    void showLoadingEmpty();
}
