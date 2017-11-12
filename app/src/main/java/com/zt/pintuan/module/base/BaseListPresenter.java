package com.zt.pintuan.module.base;

import android.os.Bundle;

import com.zt.pintuan.model.Response;
import com.zt.pintuan.base.ReturnCode;
import com.zt.pintuan.network.exception.ErrorThrowable;

import java.util.ArrayList;
import java.util.List;

import icepick.State;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by caiyk on 2017/1/12.
 * Email:781208202@qq.com
 */

public abstract class BaseListPresenter<D, V extends IListView> extends BasePresenter<V> {
    protected static final int REQUEST_REFRESH = 0x272727;
    @State
    public int mPageId = 1;
    public List<D> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(REQUEST_REFRESH, () -> getListData(mPageId)
                        .flatMap(responseReply -> getDataObservable(responseReply))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                , (view, response) -> {
                    if (!response.isSuccess()) {
                        if (mPageId == 1) {
                            view.showLoadingEmpty();
                        } else {
                            if (mDataList != response.data) {
                                mDataList = response.data;
                                view.showListItems(mPageId == 1, mDataList);
                            } else {
                                view.showLoadMoreState(response.code, response.msg);
                            }
                        }
                    } else {
                        view.showListItems(mPageId == 1, mDataList);
                    }
                }, (view, throwable) -> view.showLoadingRetry(ErrorThrowable.convertErr(throwable).code, ErrorThrowable.convertErr(throwable).msg));
    }

    private Observable<? extends Response<List<D>>> getDataObservable(Response<List<D>> response) {
        if (response == null) {
            return Observable.error(new ErrorThrowable(ReturnCode.LOCAL_UNKNOWN_ERROR, null));
        }
        if (response.isListEmpty()) response.code = ReturnCode.CODE_EMPTY;
        mDataList = mDataList != null ? mDataList : new ArrayList<>();
        if (mPageId == 1) {
            mDataList.clear();
        }
        if (!response.isListEmpty()) mDataList.addAll(response.data);
        response.data = mDataList;
        return Observable.just(response);
    }

    public void requestListData(boolean isRefresh) {
        this.mPageId = isRefresh ? 1 : (mPageId + 1);

        start(REQUEST_REFRESH);
    }

    public abstract Observable<Response<List<D>>> getListData(int pageId);
}
