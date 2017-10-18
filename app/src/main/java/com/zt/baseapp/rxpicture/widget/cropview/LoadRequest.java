package com.zt.baseapp.rxpicture.widget.cropview;

import android.graphics.RectF;
import android.net.Uri;

import com.zt.baseapp.rxpicture.widget.cropview.callback.LoadCallback;

import rx.Completable;


public class LoadRequest {

    private float initialFrameScale;
    private RectF initialFrameRect;
    private boolean useThumbnail;
    private CropImageView cropImageView;
    private Uri sourceUri;

    public LoadRequest(CropImageView cropImageView, Uri sourceUri) {
        this.cropImageView = cropImageView;
        this.sourceUri = sourceUri;
    }

    public LoadRequest initialFrameScale(float initialFrameScale) {
        this.initialFrameScale = initialFrameScale;
        return this;
    }

    public LoadRequest initialFrameRect(RectF initialFrameRect) {
        this.initialFrameRect = initialFrameRect;
        return this;
    }

    public LoadRequest useThumbnail(boolean useThumbnail) {
        this.useThumbnail = useThumbnail;
        return this;
    }

    public void execute(LoadCallback callback) {
        if (initialFrameRect == null) {
            cropImageView.setInitialFrameScale(initialFrameScale);
        }
        cropImageView.loadAsync(sourceUri, useThumbnail, initialFrameRect, callback);
    }

    public Completable executeAsCompletable() {
        if (initialFrameRect == null) {
            cropImageView.setInitialFrameScale(initialFrameScale);
        }
        return cropImageView.loadAsCompletable(sourceUri, useThumbnail, initialFrameRect);
    }
}
