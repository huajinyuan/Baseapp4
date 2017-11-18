package com.example.zhongchou.rxpicture.utils;

import android.net.Uri;

import com.example.zhongchou.rxpicture.widget.cropview.CropImageView;

import java.io.Serializable;

/**
 * Created by caiyk on 2017/9/23.
 */

public class CropConfig implements Serializable {
    private Uri mUri;
    private CropImageView.CropMode mCropMode = CropImageView.CropMode.SQUARE;
    private CropImageView.ShowMode mShowMode = CropImageView.ShowMode.SHOW_ALWAYS;

    public CropConfig() {
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    public CropImageView.CropMode getCropMode() {
        return mCropMode;
    }

    public void setCropMode(CropImageView.CropMode cropMode) {
        mCropMode = cropMode;
    }

    public CropImageView.ShowMode getShowMode() {
        return mShowMode;
    }

    public void setShowMode(CropImageView.ShowMode showMode) {
        mShowMode = showMode;
    }
}
