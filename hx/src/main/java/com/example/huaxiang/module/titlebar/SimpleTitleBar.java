package com.example.huaxiang.module.titlebar;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;


/**
 * Created by caiyk on 2017/1/11.
 * Email:781208202@qq.com
 */

public class SimpleTitleBar implements ITitleBar {
    private ViewGroup rlToolbar;
    private ImageView ivLeft;
    private TextView tvTitle;
    private TextView tvRight;

    public static SimpleTitleBar newInstance() {
        SimpleTitleBar simpleTitleBar = new SimpleTitleBar();
        return simpleTitleBar;
    }

    @Override
    public int getTitleLayout() {
        return R.layout.titlebar_simple;
    }

    @Override
    public void initTitleView(Activity activity, ViewGroup view, TitleBarBuilder titleBarBuild) {
        ivLeft = (ImageView) view.findViewById(R.id.ivLeft);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        rlToolbar = (ViewGroup) view.findViewById(R.id.rlToolbar);
        tvRight = (TextView) view.findViewById(R.id.tvRight);
        if (titleBarBuild.background != 0) {
            rlToolbar.setBackgroundColor(ContextCompat.getColor(activity, titleBarBuild.background));
        }

        if (titleBarBuild.leftImageRes != 0) ivLeft.setImageResource(titleBarBuild.leftImageRes);
        ivLeft.setVisibility(titleBarBuild.leftVisible);
        ivLeft.setOnClickListener(v -> {
            if (titleBarBuild.leftVisible != View.VISIBLE) return;
            if (titleBarBuild.onLeftClickListener == null) {
                if (activity != null) {
                    activity.finish();
                }
            } else {
                titleBarBuild.onLeftClickListener.onClick(v);
            }
        });
        tvTitle.setOnClickListener(v -> {
            if (titleBarBuild.centerVisible != View.VISIBLE) return;
            if (titleBarBuild.onCenterClickListener == null) {
                return;
            } else {
                titleBarBuild.onCenterClickListener.onClick(v);
            }
        });

        tvTitle.setText(titleBarBuild.title);

        if (!TextUtils.isEmpty(titleBarBuild.rightString)) {
            tvRight.setText(titleBarBuild.rightString);
            tvRight.setOnClickListener(v -> {
                if (titleBarBuild.onRightClickListener == null) return;
                titleBarBuild.onRightClickListener.onClick(v);
            });
        }
    }

}
