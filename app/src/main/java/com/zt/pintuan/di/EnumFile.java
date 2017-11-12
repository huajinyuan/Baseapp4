package com.zt.pintuan.di;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.zt.pintuan.di.EnumFile.CACHE;
import static com.zt.pintuan.di.EnumFile.EXTERIOR;
import static com.zt.pintuan.di.EnumFile.NETWORK;
import static com.zt.pintuan.di.EnumFile.ROOT;

/**
 * Created by caiyk on 2017/2/20.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        EXTERIOR,
        ROOT,
        CACHE,
        NETWORK
})
public @interface EnumFile {
    String EXTERIOR = "";
    String ROOT = "root";
    String CACHE = "cache";
    String NETWORK = "network";
}
