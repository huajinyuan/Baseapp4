package com.example.huaxiang.module.base;

import android.support.annotation.ColorRes;

/**
 * Created by caiyk on 2016/12/16.
 */
public class StatusBarValue {
    //状态栏颜色
    private int statusBarColor;

    //状态栏布局模式
    private LayoutMode layoutMode = LayoutMode.BELOW_TITLE_BAR;

    public enum LayoutMode {
        //全屏
        FULLSCREEN,
        //内容布局在时间状态栏下
        BELOW_STATE_BAR,
        //内容布局在标题栏下
        BELOW_TITLE_BAR
    }

    public StatusBarValue setLayoutMode(LayoutMode layoutMode) {
        this.layoutMode = layoutMode;
        return this;
    }

    public StatusBarValue setStatusBarColor(@ColorRes int color) {
        this.statusBarColor = color;
        return this;
    }

    public LayoutMode getLayoutMode() {
        return layoutMode;
    }

    public int getStatusBarColor() {
        return statusBarColor;
    }
}
