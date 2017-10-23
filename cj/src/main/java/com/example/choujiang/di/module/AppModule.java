package com.example.choujiang.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;


import com.example.choujiang.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by caiyk on 2017/1/10.
 * Email:781208202@qq.com
 */
@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    @ApplicationContext
    public Context providesAppContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    public Application provideApp() {
        return mApplication;
    }

    @Provides
    @Singleton
    public Resources provideResources() {
        return mApplication.getResources();
    }
}
