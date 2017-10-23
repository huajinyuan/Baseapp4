package com.example.huaxiang.module.titlebar;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 */

public class TitleBarBuilder {
    private Context mContext;

    protected ITitleBar iTitleBar;

    protected String title = "";

    protected int leftImageRes;
    protected int rightImageRes;

    protected String rightString = "";

    protected int leftVisible;
    protected int rightVisible;
    protected int centerVisible;

    protected int background;

    protected View.OnClickListener onCenterClickListener;
    protected View.OnClickListener onLeftClickListener;
    protected View.OnClickListener onRightClickListener;

    public TitleBarBuilder(Context context) {
        this.mContext = context;
    }

    public TitleBarBuilder config(ITitleBar iTitleBar) {
        this.iTitleBar = iTitleBar;
        return this;
    }

    public <T extends ITitleBar> TitleBarBuilder config(Class<T> tClass) {
        try {
            this.iTitleBar = (ITitleBar) Class.forName(tClass.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public TitleBarBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TitleBarBuilder setTitle(@StringRes int titleRes) {
        return setTitle(mContext.getString(titleRes));
    }

    public TitleBarBuilder setRightText(String rightString) {
        this.rightString = rightString;
        return this;
    }

    public TitleBarBuilder setRightText(@StringRes int rightStringRes) {
        return setRightText(mContext.getString(rightStringRes));
    }

    public TitleBarBuilder setRightImage(@DrawableRes int rightImageRes) {
        this.rightImageRes = rightImageRes;
        return this;
    }

    public TitleBarBuilder setLeftImage(@DrawableRes int leftImage) {
        this.leftImageRes = leftImage;
        return this;
    }

    public TitleBarBuilder setLeftVisible(boolean visible) {
        this.leftVisible = visible ? View.VISIBLE : View.INVISIBLE;
        return this;
    }

    public TitleBarBuilder setRightVisible(boolean visible) {
        this.rightVisible = visible ? View.VISIBLE : View.INVISIBLE;
        return this;
    }

    public TitleBarBuilder setCenterVisible(boolean visible) {
        this.centerVisible = visible ? View.VISIBLE : View.INVISIBLE;
        return this;
    }

    public TitleBarBuilder setBackground(int background) {
        this.background = background;
        return this;
    }

    public TitleBarBuilder setOnLeftClickListener(View.OnClickListener onClickListener) {
        this.onLeftClickListener = onClickListener;
        return this;
    }

    public TitleBarBuilder setOnRightClickListener(View.OnClickListener onClickListener) {
        this.onRightClickListener = onClickListener;
        return this;
    }

    public TitleBarBuilder setOnCenterClickListener(View.OnClickListener onClickListener) {
        this.onCenterClickListener = onClickListener;
        return this;
    }


    public boolean isShowTitle() {
        return iTitleBar != null;
    }

    public void clear() {
        iTitleBar = null;
        onCenterClickListener = null;
        onLeftClickListener = null;
        onRightClickListener = null;
    }
}
