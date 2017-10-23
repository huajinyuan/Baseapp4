package com.example.choujiang.module.titlebar;

import android.app.Activity;
import android.view.ViewGroup;

/**
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 */

public interface ITitleBar {
    int getTitleLayout();

    void initTitleView(Activity activity, ViewGroup view, TitleBarBuilder titleBarBuild);
}
