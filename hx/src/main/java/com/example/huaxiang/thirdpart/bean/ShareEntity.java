package com.example.huaxiang.thirdpart.bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShareEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // 标题
    private String title = null;
    // 内容
    private String content = null;
    // 分享的网页的URL
    private String webpageUrl = null;
    // 分享的视频的URL
    private String videoUrl = null;
    // 分享的音频的URL
    private String musicUrl = null;
    // 网络图片URL,QQ好友
    private String networkImageUrl = null;
    // 网络多张图片URL，只在分享到QQ空间中使用
    private ArrayList<String> networkImageUrls = null;
    // 新浪，微信分享图片的bitmap
    private Bitmap bitmap = null;

    //新浪的多媒体描述
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }

    /**
     * 获得title
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title
     *
     * @param title
     */
    void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获得内容
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content
     */
    void setContent(String content) {
        this.content = content;
    }

    /**
     * 获得分享网页的URL
     *
     * @return
     */
    public String getWebpageUrl() {
        return webpageUrl;
    }

    /**
     * 设置分享网页的URL
     *
     * @param webpageUrl
     */
    void setWebpageUrl(String webpageUrl) {
        this.webpageUrl = webpageUrl;
    }

    /**
     * 获得分享视频的URL
     *
     * @return
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * 设置分享视频的URL
     *
     * @param videoUrl
     */
    void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * 获得分享音频的URL
     *
     * @return
     */
    public String getMusicUrl() {
        return musicUrl;
    }

    /**
     * 设置分享音频的URL
     *
     * @param musicUrl
     */
    void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    /**
     * 获得分享qq网络图片的URL
     *
     * @return
     */
    public String getNetworkImageUrl() {
        return networkImageUrl;
    }

    /**
     * 设置分享qq网络图片的URL
     *
     * @param networkImageUrl
     */
    void setNetworkImageUrl(String networkImageUrl) {
        this.networkImageUrl = networkImageUrl;
    }

    /**
     * 获得分享到QQ空间的多张网络图片的URL
     *
     * @return
     */
    public ArrayList<String> getNetworkImageUrls() {
        return networkImageUrls;
    }

    /**
     * 设置分享到QQ空间的多张网络图片的URL
     *
     * @param urls
     */
    void setNetworkImageUrls(List<String> urls) {
        this.networkImageUrls = new ArrayList<String>(urls);
    }

    /**
     * 获得分享到新浪，微信的图片
     *
     * @return
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * 设置分享微信的图片
     *
     * @param bitmap
     */
    void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * 设置分享到QQ的图片 纯图片分享
     *
     * @param localImagePath
     */
    public void setLocalImagePath(String localImagePath) {
        this.localImagePath = localImagePath;
    }

    private String localImagePath = null;
}
