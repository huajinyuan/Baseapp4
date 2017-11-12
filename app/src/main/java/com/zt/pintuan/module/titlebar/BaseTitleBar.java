package com.zt.pintuan.module.titlebar;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 */

public class BaseTitleBar {

    private ViewGroup mRootTitleBar;
    private TitleBarBuilder mTitleBarBuild;

    public void attach(Activity context, TitleBarBuilder titleBarBuild) {
        this.mTitleBarBuild = titleBarBuild;
        if (titleBarBuild.iTitleBar == null) {
            return;
        }
        initTitleBar(context);
    }

    private void initTitleBar(Activity context) {
        mRootTitleBar = (ViewGroup) LayoutInflater.from(context).inflate(mTitleBarBuild.iTitleBar.getTitleLayout(), null);
        mTitleBarBuild.iTitleBar.initTitleView(context, mRootTitleBar, mTitleBarBuild);
    }

    public ViewGroup getTitleBarView() {
        return mRootTitleBar;
    }

    public TitleBarBuilder getTitleBarBuild() {
        return mTitleBarBuild;
    }

    public <V extends View> V getView(@IdRes int resId) {
        return (V) mRootTitleBar.findViewById(resId);
    }

    public void detach() {
        mTitleBarBuild.clear();
        mTitleBarBuild = null;
        mRootTitleBar = null;
    }
}
