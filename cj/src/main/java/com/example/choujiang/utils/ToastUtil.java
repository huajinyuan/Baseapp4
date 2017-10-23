package com.example.choujiang.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;


/**
 * <简易Toast 保持只有一个实例，防止多次调用时长时间依次显示问题>
 */
public class ToastUtil {
    private static Toast sToast;


    public static void showToast(String msg) {
        showToast(AppContext.getContext(), msg);
    }

    public static void showToast(@StringRes int msgResId) {
        showToast(AppContext.getContext(), AppContext.getContext().getResources().getString(msgResId));
    }

    public static void showToast(Context context, @StringRes int msgResId) {
        showToast(context, context.getResources().getString(msgResId));
    }

    public static void showToast(Context context, String msg) {
        try {
            if (msg == null || msg.trim().length() == 0) {
                return;
            }
            if (sToast == null) {
                sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
                sToast.show();
            } else {
                sToast.setText(msg);
                sToast.setDuration(Toast.LENGTH_SHORT);
                sToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLongToast(Context context, @StringRes int msgResId) {
        showLongToast(context, context.getResources().getString(msgResId));
    }

    public static void showLongToast(Context context, String msg) {
        try {
            if (msg == null || msg.trim().length() == 0) {
                return;
            }
            if (sToast == null) {
                sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
                sToast.show();
            } else {
                sToast.setText(msg);
                sToast.setDuration(Toast.LENGTH_LONG);
                sToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
        }
    }
}
