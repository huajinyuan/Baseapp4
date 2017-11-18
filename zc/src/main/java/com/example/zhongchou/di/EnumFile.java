package com.example.zhongchou.di;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.zhongchou.di.EnumFile.CACHE;
import static com.example.zhongchou.di.EnumFile.EXTERIOR;
import static com.example.zhongchou.di.EnumFile.NETWORK;
import static com.example.zhongchou.di.EnumFile.ROOT;

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
