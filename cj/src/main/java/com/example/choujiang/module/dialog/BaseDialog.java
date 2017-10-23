package com.example.choujiang.module.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.choujiang.R;
import com.zhy.autolayout.utils.AutoUtils;

public abstract class BaseDialog extends AlertDialog implements View.OnClickListener {
    protected Context mContext;
    private boolean closeOutside;
    protected OnDialogRightListener mOnDialogRightListener;
    protected OnDialogLeftListener mOnDialogLeftListener;

    protected BaseDialog(Context context) {
        this(context, true);
    }

    protected BaseDialog(Context context, boolean closeOutside) {
        super(context, R.style.fullscreen_dialog);
        this.closeOutside = closeOutside;
        this.mContext = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        LayoutParams clp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View content = getContentView(layoutInflater);
        initView(content);
        View layout = getBaseDialogView(layoutInflater);

        layout.findViewById(R.id.dialog_top).setOnClickListener(this);
        layout.findViewById(R.id.dialog_bottom).setOnClickListener(this);

        LinearLayout contentLayout = (LinearLayout) layout.findViewById(R.id.dialog_content);
        contentLayout.addView(content, clp);

        setContentView(layout, clp);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        AutoUtils.autoSize(layout); //适配

        WindowManager.LayoutParams lParams = getWindow().getAttributes();
        lParams.gravity = Gravity.CENTER;
        lParams.width = LayoutParams.MATCH_PARENT;
        lParams.height = LayoutParams.MATCH_PARENT;
        lParams.alpha = 1.0f;
        lParams.dimAmount = 0.0f;
        getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        getWindow().setAttributes(lParams);
    }

    public View getBaseDialogView(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.dialog_layout, null);
    }

    @Override
    public void onClick(View v) {
        if (closeOutside && (v.getId() == R.id.dialog_top || v.getId() == R.id.dialog_bottom)) {
            this.dismiss();
        }
    }

    public abstract View getContentView(LayoutInflater inflater);

    protected abstract void initView(View content);


    public BaseDialog setOnDialogRightListener(OnDialogRightListener onDialogRightListener) {
        mOnDialogRightListener = onDialogRightListener;
        return this;
    }

    public BaseDialog setOnDialogLeftListener(OnDialogLeftListener onDialogLeftListener) {
        mOnDialogLeftListener = onDialogLeftListener;
        return this;
    }

    public interface OnDialogRightListener {
        void click();
    }

    public interface OnDialogLeftListener {
        void click();
    }


    public static class DialogContent {
        public String strLeft = "";
        public String strRight = "";
        public String strTitle = "";
        public String strContent = "";
        public String strSubTitle = "";
        public int leftColor;
        public int rightColor;

        public DialogContent() {
            this("取消", "确定");
        }

        public DialogContent(String strLeft, String strRight) {
            this.strLeft = strLeft;
            this.strRight = strRight;
        }

        public DialogContent setTitle(String title) {
            strTitle = title;
            return this;
        }

        public DialogContent setContent(String content) {
            strContent = content;
            return this;
        }

        public DialogContent setLeftColor(@ColorInt int leftColor) {
            this.leftColor = leftColor;
            return this;
        }

        public DialogContent setRightColor(@ColorInt int rightColor) {
            this.rightColor = rightColor;
            return this;
        }

        public DialogContent setSubTitle(String strSubTitle) {
            this.strSubTitle = strSubTitle;
            return this;
        }
    }

}
