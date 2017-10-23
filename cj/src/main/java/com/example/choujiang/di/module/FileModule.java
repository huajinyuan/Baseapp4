package com.example.choujiang.di.module;

import android.content.Context;


import com.example.choujiang.di.ApplicationContext;
import com.example.choujiang.di.EnumFile;
import com.example.choujiang.utils.FileUtils;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by caiyk on 2017/2/20.
 */
@Module
public class FileModule {
    @Singleton
    @Provides
    @Named(EnumFile.EXTERIOR)
    public File provideExteriorFile(@ApplicationContext Context context) {
        File file = FileUtils.getExternalFile(context);
        return file;
    }

    @Singleton
    @Provides
    @Named(EnumFile.ROOT)
    public File provideDirFile(@ApplicationContext Context context) {
        File file = FileUtils.getDiskCacheDir(context, "");
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    @Provides
    @Singleton
    @Named(EnumFile.CACHE)
    public File providerCacheFile(@Named(EnumFile.ROOT) File rootFile) {
        File file = new File(rootFile, EnumFile.CACHE);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    @Provides
    @Singleton
    @Named(EnumFile.NETWORK)
    public File providerNetworkFile(@Named(EnumFile.ROOT) File rootFile) {
        File file = new File(rootFile, EnumFile.NETWORK);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

}
