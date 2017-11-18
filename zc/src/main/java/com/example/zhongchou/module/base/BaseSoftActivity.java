package com.example.zhongchou.module.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zhongchou.utils.BarUtils;
import com.zhy.autolayout.utils.AutoUtils;

import nucleus.presenter.Presenter;

/**
 * Created by caiyk on 2017/5/6.
 */

public abstract class BaseSoftActivity<P extends Presenter> extends BaseActivity<P> {
    @Override
    public void setContentView(View contentView) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout rootView = new LinearLayout(this);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setLayoutParams(params);
        rootView.addView(contentView, params);
        rootView.addView(getBaseTitleBar().getTitleBarView(), 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AutoUtils.getPercentHeightSize(100)));
        getBaseTitleBar().getTitleBarView().setBackgroundColor(getResources().getColor(getStatusBar().getStatusBarColor()));
        BarUtils.setColor(this, getResources().getColor(getStatusBar().getStatusBarColor()), 0);
        getDelegate().setContentView(rootView);
    }
}
