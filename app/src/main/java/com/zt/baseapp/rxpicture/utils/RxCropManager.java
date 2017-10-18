package com.zt.baseapp.rxpicture.utils;

import android.net.Uri;

import com.zt.baseapp.rxpicture.widget.cropview.CropImageView;

/**
 * Created by caiyk on 2017/9/23.
 */

public class RxCropManager {
    private CropConfig mCropConfig;

    private static final class RxCropManagerHolder {
        private static final RxCropManager INSTANCE = new RxCropManager();
    }

    public static final RxCropManager getInstance() {
        return RxCropManagerHolder.INSTANCE;
    }

    public RxCropManager() {
        mCropConfig = new CropConfig();
    }

    public CropConfig getCropConfig() {
        return mCropConfig;
    }

    public RxCropManager setCropConfig(CropConfig cropConfig) {
        mCropConfig = cropConfig;
        return this;
    }

    public RxCropManager setCropMode(CropImageView.CropMode cropMode) {
        mCropConfig.setCropMode(cropMode);
        return this;
    }

    public RxCropManager setShowMode(CropImageView.ShowMode showMode) {
        mCropConfig.setShowMode(showMode);
        return this;
    }

    public RxCropManager setUril(Uri uri) {
        mCropConfig.setUri(uri);
        return this;
    }
}
