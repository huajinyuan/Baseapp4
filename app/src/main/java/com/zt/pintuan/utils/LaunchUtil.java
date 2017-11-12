package com.zt.pintuan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


/**
 * <请描述这个类是干什么的>
 *
 * @author Caiyk
 * @data: 2016/12/21
 * @version: v1.0
 */

public class LaunchUtil {
    private LaunchUtil() {
    }

    public static boolean launchActivity(Context context, Class<? extends Activity> cls) {
        return launchActivity(context, cls, null);
    }

    public static boolean launchActivity(Context context, Class<? extends Activity> cls, Bundle bundle) {
        return launchActivity(context, cls, bundle, 0);
    }

    public static boolean launchActivity(Context context, Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode != 0) {
            ((Activity) context).startActivityForResult(intent, requestCode);
        } else {
            context.startActivity(intent);
        }
        return true;
    }

/*    public static boolean launchSimpleWeb(Context context, String url, String title) {
        WebConfig webConfig = new WebConfig(url, title, true);
        return launchActivity(context, SimpleWebActivity.class, SimpleWebActivity.buildBundle(webConfig));
    }*/

    public static boolean launchActivityWithFlag(Context context, Class cls, int flag, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (flag != -1) {
            intent.setFlags(flag);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        return true;
    }
}
