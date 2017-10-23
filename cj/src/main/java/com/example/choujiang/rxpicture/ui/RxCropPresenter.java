package com.example.choujiang.rxpicture.ui;

import android.os.Bundle;

import com.example.choujiang.di.BaseAppManager;
import com.example.choujiang.di.EnumFile;
import com.example.choujiang.module.base.BasePresenter;
import com.example.choujiang.utils.TimeUtils;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by caiyk on 2017/9/28.
 */

public class RxCropPresenter extends BasePresenter<RxCropActivity> {
    @Inject
    @Named(EnumFile.CACHE)
    public File mCacheFile;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        BaseAppManager.getInstance().inject(this);
    }

    public String getFilePath() {
        String fileName = "scv" + TimeUtils.getNowTimeString("yyyyMMdd_HHmmss") + ".jpeg";
        return new File(mCacheFile, fileName).getAbsolutePath();
    }
}
