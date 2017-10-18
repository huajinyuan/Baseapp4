package com.zt.baseapp.utils;

import com.orhanobut.logger.Logger;

public final class LogUtil {
    private static boolean sDebug = true;

    public static synchronized void init(boolean debug, String tag) {
        sDebug = debug;
        if (debug) {
            Logger.init(tag).hideThreadInfo().methodCount(3);
        }
    }

    public static void d(String tag, String msg) {
        if (sDebug) {
            Logger.t(tag).d(msg);
        }
    }

    public static void d(String msg) {
        if (sDebug) {
            Logger.d(msg);
        }
    }

    public static void e(String msg) {
        if (sDebug) {
            Logger.e(msg);
        }
    }

    public static void json(String msg) {
        if (sDebug) {
            Logger.json(msg);
        }
    }
}
