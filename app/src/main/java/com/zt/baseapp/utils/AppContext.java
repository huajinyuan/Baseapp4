package com.zt.baseapp.utils;

import android.content.Context;


/**
 * 应用初始化信息配置，
 */
public class AppContext {
    private static Context context;
    private static volatile AppContext appContext;
    public static String DEVICE_ID = "9774d56d682e549c";
    public static String VERSION_NAME = "1.0.0";

    private AppContext() {
    }

    public static Context getContext() {
        return context;
    }

    public static String getString(int resId) {
        return context.getResources().getString(resId);
    }

    public synchronized static AppContext getInstance() {
        if (appContext == null) {
            synchronized (AppContext.class) {
                if (appContext == null) {
                    appContext = new AppContext();
                }
            }
        }
        return appContext;
    }

    public synchronized void init(Context context) {
        AppContext.context = context;
        VERSION_NAME = AppUtils.getAppVersionName(context);
        try {
            DEVICE_ID = DeviceUidGenerator.generate(context);
        } catch (Exception e) {
            DEVICE_ID = "9774d56d682e549c";
        }
    }

    public static boolean isNetworkAvailable() {
        return Utils.isNetworkAvailable(context);
    }

}
