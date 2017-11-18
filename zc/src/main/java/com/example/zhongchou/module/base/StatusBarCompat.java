package com.example.zhongchou.module.base;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zhongchou.R;
import com.example.zhongchou.module.titlebar.AutoToolbar;
import com.example.zhongchou.utils.statusbar.AndroidMHelper;
import com.example.zhongchou.utils.statusbar.FlymeHelper;
import com.example.zhongchou.utils.statusbar.MIUIHelper;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * <沉浸式工具类>
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 */

public class StatusBarCompat {
    //TODO 后续需要用Merge标签优化布局层级

    /**
     * 1. merge标签只能作为xml文件的根节点,就是最外层的那个
     * 2. merge只能合并FrameLayout哦,如果根节点为LinearLayout或者其他,再使用<merge>标签就会报错哦！
     * 3. 使用LayoutInflater的inflate方法加载<merge>标签的布局文件时,需要为他指定一个父容器控件,
     * 并且设置attachToRoot属性为true!!!
     *
     * @param activity
     * @param activityContentView
     * @param titleBarView
     * @param statusBarValue
     * @return
     */
    public static View initStatusBarView(BaseAutoLayoutActivity activity, View activityContentView, View titleBarView, StatusBarValue statusBarValue) {
        if (!activity.isWindowTranslucentStatus() || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            // Android Version < 4.4  沉浸式只支持V4.4及以上的Android版本
            if (titleBarView == null) {
                return activityContentView;
            } else {
                if (statusBarValue.getLayoutMode() == StatusBarValue.LayoutMode.BELOW_TITLE_BAR) {
                    return getActionBarRootView(activity, titleBarView, activityContentView);
                } else {
                    AutoRelativeLayout activityRootView = (AutoRelativeLayout) LayoutInflater.from(activity).inflate(R.layout.activity_base_titlebar_no_statebar, null);
                    handleToolBar(titleBarView, activityRootView);
                    if (activityContentView != null) {
                        activityRootView.addView(activityContentView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    }
                    return activityRootView;
                }
            }
        } else {
            if (titleBarView == null) {
                if (statusBarValue.getLayoutMode() != StatusBarValue.LayoutMode.FULLSCREEN) {
                    AutoLinearLayout activityRootView = new AutoLinearLayout(activity);
                    activityRootView.setOrientation(LinearLayout.VERTICAL);
                    activityRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    activityRootView.addView(getStateBarView(activity, statusBarValue), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
                    if (activityContentView != null) {
                        activityRootView.addView(activityContentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    }
                    return activityRootView;
                } else {
                    return activityContentView;
                }
            } else {
                return getActivityRootViewWithTitleBar(activity, activityContentView, titleBarView, statusBarValue);
            }
        }
    }

    private static void handleToolBar(View titleBarView, RelativeLayout activityRootView) {
        AutoToolbar toolbar = (AutoToolbar) activityRootView.findViewById(R.id.toolbar);
        toolbar.addView(titleBarView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private static View getActivityRootViewWithTitleBar(BaseAutoLayoutActivity activity, View activityContentView, View titleBarView, StatusBarValue statusBarValue) {
        if (statusBarValue.getLayoutMode() == StatusBarValue.LayoutMode.FULLSCREEN) {
            AutoRelativeLayout activityRootView = (AutoRelativeLayout) LayoutInflater.from(activity).inflate(R.layout.activity_base_titlebar_fullscreen, null);
            if (activityContentView != null) {
                activityRootView.addView(activityContentView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            ((LinearLayout) activityRootView.findViewById(R.id.llTitle)).addView(getStateBarView(activity, statusBarValue), 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            handleToolBar(titleBarView, activityRootView);

            return activityRootView;
        } else if (statusBarValue.getLayoutMode() == StatusBarValue.LayoutMode.BELOW_STATE_BAR) {
            RelativeLayout activityRootView = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.activity_base_titlebar_below_statebar, null);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.BELOW, R.id.llStateBar);
            if (activityContentView != null) {
                activityRootView.addView(activityContentView, 1, lp);
            }
            ((ViewGroup) activityRootView.findViewById(R.id.llStateBar)).addView(getStateBarView(activity, statusBarValue), 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            handleToolBar(titleBarView, activityRootView);

            return activityRootView;
        } else {
            LinearLayout activityRootView = getActionBarRootView(activity, titleBarView, activityContentView);
            activityRootView.addView(getStateBarView(activity, statusBarValue), 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            return activityRootView;
        }
    }

    private static View getStateBarView(BaseAutoLayoutActivity activity, StatusBarValue statusBarValue) {
        View statusBarView = new View(activity);
        statusBarView.setBackgroundColor(activity.getResources().getColor(statusBarValue.getStatusBarColor()));
        int white = ContextCompat.getColor(activity, R.color.white);
        int transparent = ContextCompat.getColor(activity, android.R.color.transparent);
        int color = ContextCompat.getColor(activity, statusBarValue.getStatusBarColor());
        if (color == white || color == transparent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                new MIUIHelper().setStatusBarLightMode(activity, true);
                new FlymeHelper().setStatusBarLightMode(activity, true);
                new AndroidMHelper().setStatusBarLightMode(activity, true);
            }
        }
        return statusBarView;
    }

    private static LinearLayout getActionBarRootView(BaseAutoLayoutActivity activity, View titleBarView, View activityContentView) {
        LinearLayout activityRootView = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.activity_base, null);
        Toolbar toolbar = (Toolbar) activityRootView.findViewById(R.id.toolbar);
        toolbar.addView(titleBarView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (activityContentView != null) {
            activityRootView.addView(activityContentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        return activityRootView;
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
