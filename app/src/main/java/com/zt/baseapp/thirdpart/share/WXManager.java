package com.zt.baseapp.thirdpart.share;

import android.app.Activity;
import android.graphics.Bitmap;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zt.baseapp.base.ReturnCode;
import com.zt.baseapp.network.exception.ErrorThrowable;
import com.zt.baseapp.thirdpart.ConstantParams;
import com.zt.baseapp.thirdpart.bean.EnumPlatform;
import com.zt.baseapp.thirdpart.bean.ShareEntity;
import com.zt.baseapp.thirdpart.util.SocialUtil;

import java.lang.ref.SoftReference;

import rx.subjects.PublishSubject;

/**
 * Created by caiyk on 2017/9/13.
 */

public class WXManager extends AbstractComponent {
    private PublishSubject<Boolean> mPublishSubject = PublishSubject.create();
    private SoftReference<Activity> mActivitySoftReference;
    private IWXAPI mIWXAPI;

    public WXManager(Activity activity) {
        this.mActivitySoftReference = new SoftReference<>(activity);
        if (this.mIWXAPI == null) {
            mIWXAPI = WXAPIFactory.createWXAPI(activity, ConstantParams.WX_APP_ID,true);
        }
        mIWXAPI.registerApp(ConstantParams.WX_APP_ID);
    }

    @Override
    public PublishSubject<Boolean> share(EnumPlatform.Method method, ShareEntity shareEntity) {
        if (!mIWXAPI.isWXAppInstalled()) {
            mPublishSubject.onError(new ErrorThrowable(ReturnCode.CODE_ERROR, "未安装微信"));
        } else if (shareEntity == null) {
            mPublishSubject.onError(new ErrorThrowable(ReturnCode.CODE_EMPTY, "分享内容为空"));
        } else {
            WXMediaMessage msg = new WXMediaMessage();
            msg.title = SocialUtil.handleStr(shareEntity.getTitle());
            msg.description = SocialUtil.handleStr(shareEntity.getContent());
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());

            if (shareEntity.getWebpageUrl() != null) {
                // 网页类型分享
                WXWebpageObject webpageObj = new WXWebpageObject();
                webpageObj.webpageUrl = SocialUtil.handleStr(shareEntity.getWebpageUrl());
                SocialUtil.buildThumbBmp(msg, shareEntity.getBitmap());
                msg.mediaObject = webpageObj;
            } else if (shareEntity.getVideoUrl() != null) {
                // 视频类型分享
                WXVideoObject video = new WXVideoObject();
                video.videoUrl = SocialUtil.handleStr(shareEntity.getVideoUrl());
                SocialUtil.buildThumbBmp(msg, shareEntity.getBitmap());
                msg.mediaObject = video;
            } else if (shareEntity.getMusicUrl() != null) {
                // 音频类型分享
                WXMusicObject musicObj = new WXMusicObject();
                musicObj.musicUrl = SocialUtil.handleStr(shareEntity.getMusicUrl());
                Bitmap bmp = shareEntity.getBitmap();
                SocialUtil.buildThumbBmp(msg, bmp);
                msg.mediaObject = musicObj;
            } else if (shareEntity.getBitmap() != null) {
                // 图片类型分享
                Bitmap bmp = shareEntity.getBitmap();
                WXImageObject imgObj = new WXImageObject(bmp);
                SocialUtil.buildThumbBmp(msg, bmp);
                msg.mediaObject = imgObj;
            } else {
                // 文本分享
                WXTextObject textObject = new WXTextObject();
                textObject.text = SocialUtil.handleStr(shareEntity.getContent());
                msg.mediaObject = textObject;
            }
            req.message = msg;
            switch (method) {
                case SHARE_WX_FRIEND:
                    req.scene = SendMessageToWX.Req.WXSceneSession;
                    break;
                case SHARE_WX_TIMELINE:
                    req.scene = SendMessageToWX.Req.WXSceneTimeline;
                    break;
                default:
                    break;
            }
            mIWXAPI.sendReq(req);
        }
        return mPublishSubject;
    }

}
