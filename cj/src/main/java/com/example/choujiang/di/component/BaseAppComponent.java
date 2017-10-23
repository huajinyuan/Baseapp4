package com.example.choujiang.di.component;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;


import com.example.choujiang.di.ApplicationContext;
import com.example.choujiang.di.EnumFile;
import com.example.choujiang.di.module.AppModule;
import com.example.choujiang.di.module.FileModule;
import com.example.choujiang.rxpicture.ui.RxCropPresenter;

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
