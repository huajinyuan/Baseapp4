package com.zt.pintuan.module.net;

import com.zt.pintuan.network.IBuildPublicParams;

import dagger.Module;
import okhttp3.Request;

/**
 * Created by gtgs on 17/10/13.
 */

@Module
public class ApiModule implements IBuildPublicParams {

    private String provideBaseUrl() {
        return BuildConfig.HOST_URL;
    }

    @Override
    public Request.Builder buildPublicParams(Request.Builder builder) {
        return null;
    }
}
