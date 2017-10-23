package com.example.huaxiang.module.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.config.AutoLayoutConifg;

import nucleus.presenter.Presenter;

/**
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 */
public abstract class BaseAutoLayoutActivity<P extends Presenter> extends BaseNucleusAppCompatActivity<P> {

    //0:未知 1:状态栏透明 2:状态栏不透明
    private static int IS_WINDOW_TRANSLUCENT_STATUS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initWindowTranslucentStatus(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals("FrameLayout")) {
            view = new AutoFrameLayout(context, attrs);
        } else if (name.equals("LinearLayout")) {
            view = new AutoLinearLayout(context, attrs);
        } else if (name.equals("RelativeLayout")) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }

    public void initWindowTranslucentStatus(Context context) {
        if (IS_WINDOW_TRANSLUCENT_STATUS != 0) {
            return;
        }
        BaseAutoLayoutActivity.IS_WINDOW_TRANSLUCENT_STATUS = getWindowTranslucentStatus(context) ? 1 : 2;

        if (BaseAutoLayoutActivity.IS_WINDOW_TRANSLUCENT_STATUS == 1) {
            AutoLayoutConifg.getInstance().useDeviceSize();
        }
    }

    //获取状态栏是否透明，即沉浸式状态栏
    private boolean getWindowTranslucentStatus(Context context) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowTranslucentStatus});
        boolean b = typedArray.getBoolean(0, false);
        typedArray.recycle();
        return b;
    }

    public boolean isWindowTranslucentStatus() {
        return BaseAutoLayoutActivity.IS_WINDOW_TRANSLUCENT_STATUS == 1;
    }
}
