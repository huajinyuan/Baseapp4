package com.example.zhongchou.module.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.example.zhongchou.R;
import com.example.zhongchou.model.LogoutEvent;
import com.example.zhongchou.module.dialog.ProgressDialog;
import com.example.zhongchou.module.titlebar.BaseTitleBar;
import com.example.zhongchou.module.titlebar.TitleBarBuilder;
import com.example.zhongchou.network.exception.ErrorThrowable;
import com.example.zhongchou.utils.BarUtils;
import com.example.zhongchou.utils.ToastUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import icepick.Icepick;
import nucleus.presenter.Presenter;
import rx.Observable;


/**
 * @author caiyk
 * @Description :
 * @date 2016/11/1
 */

public abstract class BaseActivity<P extends Presenter> extends BaseAutoLayoutActivity<P> implements IBaseView {
    private View contentView;
    private ProgressDialog progressDialog;
    private BaseTitleBar mBaseTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        initActivityInfo();
        getExtra();
        handlerSaveInstanceState(savedInstanceState);
        TitleBarBuilder titleBarBuild = new TitleBarBuilder(getApplicationContext());
        initTitleBar(titleBarBuild);
        mBaseTitleBar = new BaseTitleBar();
        mBaseTitleBar.attach(this, titleBarBuild);
        contentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        setContentView(contentView);
        BarUtils.setColor(this, ContextCompat.getColor(this, R.color.color_appBlue), 0);
        initView();
        initData();
        setListener();
    }

    protected void handlerSaveInstanceState(Bundle savedInstanceState) {
    }

    protected void initActivityInfo() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(StatusBarCompat.initStatusBarView(this, contentView, mBaseTitleBar.getTitleBarView(), getStatusBar()));
    }

    protected void initTitleBar(TitleBarBuilder titleBarBuild) {

    }

    public BaseTitleBar getBaseTitleBar() {
        return mBaseTitleBar;
    }

    public Observable<Void> ClickView(@IdRes int viewId) {
        return ClickView(findView(viewId));
    }

    public Observable<Void> ClickView(View view) {
        return RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS).compose(bindUntilEvent(ActivityEvent.DESTROY));
    }

    public void setLoadText(String text) {
        if (progressDialog != null) {
            progressDialog.setLoadText(text);
        }
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
            progressDialog = ProgressDialog.show(this, loadText, cancelable);
        }
        if (!progressDialog.isShowing()) {
            try {
                progressDialog.show();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void showError(Throwable throwable) {
        ToastUtil.showToast(ErrorThrowable.convertErr(throwable).msg);
    }

    @Override
    public void closeLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected View getBaseContentView() {
        return contentView;
    }

    public StatusBarValue getStatusBar() {
        return new StatusBarValue().setLayoutMode(StatusBarValue.LayoutMode.BELOW_TITLE_BAR)
                .setStatusBarColor(R.color.colorPrimary);
    }

    public void getExtra() {
    }

    protected <V extends View> V findView(@IdRes int resId) {
        return (V) contentView.findViewById(resId);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        closeLoadingDialog();
        progressDialog = null;
        mBaseTitleBar.detach();
        super.onDestroy();
    }

    protected abstract
    @LayoutRes
    int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(LogoutEvent logoutEvent) {
        Intent intent = new Intent();
        intent.setClassName("com.zhongtu.businesscard", "com.zhongtu.businesscard.module.ui.account.LoginActivity");
        intent.putExtra("isReLogin", true);
        startActivity(intent);
    }
}
