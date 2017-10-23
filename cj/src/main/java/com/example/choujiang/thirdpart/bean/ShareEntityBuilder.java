package com.example.choujiang.thirdpart.bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class ShareEntityBuilder implements Serializable {
    private ShareEntity entity;

    public ShareEntityBuilder builder() {
        this.entity = new ShareEntity();
        return this;
    }

    public ShareEntity create() {
        return this.entity;
    }

    /**
     * 设置title
     *
     * @param title
     * @return
     */
    public ShareEntityBuilder setTitle(String title) {
        this.entity.setTitle(title);
        return this;
    }

    /**
     * 设置内容
     *
     * @param content
     * @return
     */
    public ShareEntityBuilder setContent(String content) {
        this.entity.setContent(content);
        return this;
    }

    /**
     * 设置分享网页URL
     *
     * @param webpageUrl
     * @return
     */
    public ShareEntityBuilder setWebpageUrl(String webpageUrl) {
        this.entity.setWebpageUrl(webpageUrl);
        return this;
    }

    /**
     * 设置分享视频URL
     *
     * @param videoUrl
     * @return
     */
    public ShareEntityBuilder setVideoUrl(String videoUrl) {
        this.entity.setVideoUrl(videoUrl);
        return this;
    }

    /**
     * 设置分享音频URL
     *
     * @param voiceUrl
     * @return
     */
    public ShareEntityBuilder setVoiceUrl(String voiceUrl) {
        this.entity.setMusicUrl(voiceUrl);
        return this;
    }

    /**
     * 设置分享到QQ的网络图片URL
     *
     * @param networkImageUrl
     * @return
     */
    public ShareEntityBuilder setNetworkImageUrl(String networkImageUrl) {
        this.entity.setNetworkImageUrl(networkImageUrl);
        return this;
    }

    /**
     * 设置分享到QQ空间的多张网络图片的URL
     *
     * @param networkImageUrls
     * @return
     */
    public ShareEntityBuilder setNetworkImageUrls(ArrayList<String> networkImageUrls) {
        this.entity.setNetworkImageUrls(networkImageUrls);
        return this;
    }

    /**
     * 设置分享到新浪，微信的图片的Bitmap
     *
     * @param bitmap
     * @return
     */
    public ShareEntityBuilder setBitmap(Bitmap bitmap) {
        this.entity.setBitmap(bitmap);
        return this;
    }

    public ShareEntityBuilder setLocalImagePath(String localImagePath) {
        this.entity.setLocalImagePath(localImagePath);
        return this;
    }
}
