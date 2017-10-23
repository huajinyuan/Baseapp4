package com.example.huaxiang.thirdpart.share;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.huaxiang.thirdpart.ConstantParams;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, ConstantParams.WX_APP_ID);
        api.registerApp(ConstantParams.WX_APP_ID);
    }
}
