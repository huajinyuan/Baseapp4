package com.example.huaxiang.module.net;


import com.example.huaxiang.network.IBuildPublicParams;

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
