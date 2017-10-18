package com.zt.baseapp.module.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.zt.baseapp.R;
import com.zt.baseapp.module.dialog.ProgressDialog;
import com.zt.baseapp.network.exception.ErrorThrowable;
import com.zt.baseapp.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import icepick.Icepick;
import nucleus.presenter.Presenter;
import rx.Observable;

/**
 * @author caiyk
 * @Description :
 * @date 2016/11/4
 */

public abstract class BaseFragment<P extends Presenter> extends BaseNucleusSupportFragment<P> implements IBaseView {
    protected View superView;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        getExtra();
        superView = LayoutInflater.from(this.getActivity()).inflate(getLayoutId(), null);
        initView();
        initData();
        setListener();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return superView;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Icepick.saveInstanceState(this, bundle);
    }

    public Observable<Void> ClickView(@IdRes int viewId) {
        return ClickView(findView(viewId));
    }

    public Observable<Void> ClickView(View view) {
        return RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS).compose(bindToLifecycle());
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.load_loading), false);
    }

    public void showLoadingDialog(String text) {
        showLoadingDialog(text, false);
    }

    public void showLoadingDialog(String loadText, boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(getActivity(), loadText, cancelable);
        }
        if (!progressDialog.isShowing()) {
            try {
                progressDialog.show();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(Throwable throwable) {
        ToastUtil.showToast(ErrorThrowable.convertErr(throwable).msg);
    }

    public void getExtra() {
    }


    protected <V extends View> V findView(@IdRes int resId) {
        return (V) superView.findViewById(resId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        App.getRefWatcher(getActivity()).watch(this);
    }

    protected abstract
    @LayoutRes
    int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

}
