package com.zt.pintuan.di.component;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.zt.pintuan.di.ApplicationContext;
import com.zt.pintuan.di.EnumFile;
import com.zt.pintuan.di.module.AppModule;
import com.zt.pintuan.di.module.FileModule;
import com.zt.pintuan.rxpicture.ui.RxCropPresenter;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by caiyk on 2017/9/28.
 */
@Singleton
@Component(modules = {AppModule.class, FileModule.class})
public interface BaseAppComponent {

    @Named(EnumFile.EXTERIOR)
    File exteriorFile();

    @Named(EnumFile.CACHE)
    File cacheFile();

    @Named(EnumFile.ROOT)
    File rootFile();

    @Named(EnumFile.NETWORK)
    File networkFile();

    Application application();

    @ApplicationContext
    Context applicationContext();

    Resources resources();

    void inject(RxCropPresenter presenter);
}
