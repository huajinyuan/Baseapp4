package com.zt.baseapp.module.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zhy.autolayout.utils.AutoUtils;
import com.zt.baseapp.R;


/**
 * Create by：caiyk on 2015/7/16 14:01
 * Email: 781208202@qq.com
 */
public abstract class BaseAnimDialog extends AlertDialog {

    protected Context mContext;

    protected BaseAnimDialog(Context context) {
        this(context, R.style.ActionSheetDialogStyle);
    }

    protected BaseAnimDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 定义Dialog布局和参数
        View contentView = getContentView(LayoutInflater.from(mContext));
        initView(contentView);
        setContentView(contentView);
        AutoUtils.autoSize(contentView);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }

    protected abstract View getContentView(LayoutInflater inflater);

    protected abstract void initView(View contentView);

}
