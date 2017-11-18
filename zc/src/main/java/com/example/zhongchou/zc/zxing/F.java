package com.example.zhongchou.zc.zxing;



/**
 * Created by WuXiaolong
 * on 2016/4/28.
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class F {
    private static String tag = "zxing";

    private static boolean log = true;

    public static void setTag(String tag) {
        F.tag = tag;
    }

    public static void setLog(boolean log) {
        F.log = log;
    }

    public static void i(String msg) {
        if (log)
            android.util.Log.i(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (log)
            android.util.Log.i(tag, msg);
    }

    public static void d(String msg) {
        if (log)
            android.util.Log.d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (log)
            android.util.Log.d(tag, msg);
    }

    public static void w(String msg) {
        if (log)
            android.util.Log.w(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (log)
            android.util.Log.w(tag, msg);
    }

    public static void v(String msg) {
        if (log)
            android.util.Log.v(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (log)
            android.util.Log.v(tag, msg);
    }

    public static void e(String msg) {
        android.util.Log.e(tag, msg);
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }
}
