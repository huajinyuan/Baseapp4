package com.zt.pintuan.module.base;

import android.os.Bundle;

import icepick.Icepick;
import nucleus.presenter.RxPresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 */

public class BasePresenter<ViewType extends IBaseView> extends RxPresenter<ViewType> {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        Icepick.restoreInstanceState(this, savedState);
    }

    @Override
    protected void onSave(Bundle state) {
        super.onSave(state);
        Icepick.saveInstanceState(this, state);
    }

    public <T> void restartableReplayPro(int restartableId, Func0<Observable<T>> observableFactory, Action2<ViewType, T> onNext) {
        super.restartableReplay(restartableId, handlePrepare(observableFactory), handleNext(onNext), handleError());
    }

    public <T> void restartableLatestCachePro(int restartableId, Func0<Observable<T>> observableFactory, Action2<ViewType, T> onNext) {
        super.restartableLatestCache(restartableId, handlePrepare(observableFactory), handleNext(onNext), handleError());
    }

    public <T> void restartableFirstPro(int restartableId, Func0<Observable<T>> observableFactory, Action2<ViewType, T> onNext) {
        super.restartableFirst(restartableId, handlePrepare(observableFactory), handleNext(onNext), handleError());
    }

    public <T> Func0<Observable<T>> handlePrepare(Func0<Observable<T>> observableFactory) {
        return () -> view()
                .compose(deliverFirst())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(viewType -> {
                    viewType.split((viewType1, viewType2) -> viewType1.showLoadingDialog(), null);
                    return observableFactory.call();
                }).observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Action2<ViewType, T> handleNext(Action2<ViewType, T> onNext) {
        return (viewType, t) -> {
            onNext.call(viewType, t);
            viewType.closeLoadingDialog();
        };
    }

    public Action2<ViewType, Throwable> handleError() {
        return (viewType, throwable) -> {
            viewType.showError(throwable);
            viewType.closeLoadingDialog();
        };
    }

}
