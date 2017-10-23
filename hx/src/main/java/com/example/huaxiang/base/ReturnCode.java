package com.example.huaxiang.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by caiyk on 2017/2/16.
 */

public class ReturnCode {

    //未知错误
    public static final int LOCAL_UNKNOWN_ERROR = 0x10100;

    //无网络状态
    public static final int LOCAL_NO_NETWORK = 0x10101;

    //超时
    public static final int LOCAL_TIMEOUT_ERROR = 0x10102;

    //成功
    public static final int CODE_SUCCESS = 0;

    //失败
    public static final int CODE_ERROR = -1;

    //签名错误或不正确
    public static final int CODE_SIGN_ERROR = -101;

    //签名为空
    public static final int CODE_SIGN_EMPTY = -102;

    //时间戳未传
    public static final int CODE_TIME_STAMP_EMPTY = -110;

    //token过期
    public static final int CODE_TOKEN_EXPIRE = -120;

    //token无效
    public static final int CODE_TOKEN_INVALID = -121;

    //token为空
    public static final int CODE_TOKEN_EMPTY = -122;

    //参数错误
    public static final int CODE_KEY_ERROR = -130;

    //用戶名或密码错误
    public static final int CODE_USER_ERROR = -141;

    //HTTP请求类型不合法(非POST或GET)
    public static final int CODE_REQUEST_ERROR = -150;

    //数据为空
    public static final int CODE_EMPTY = 1000;

    //请求过于频繁
    public static final int CODE_REQUEST_BUSY = 2000;

    public static final int WX_TOKEN_EXPIRE = 42001;
    public static final int WX_ILLEGAL = 40001;
    public static final int WX_ERROR_SIZE = 40006;
    public static final int WX_IMAGE_SIZE = 40009;
    public static final int WX_VOICE_SIZE = 40010;
    public static final int WX_VIDEO_SIZE = 40011;
    public static final int WX_THUMB_SIZE = 40012;
    public static final int WX_TYPE_ERROR = 9001008;
    public static final int WX_MEDIA_ERROR = 40004;
    public static final int WX_FILE_ERROR = 40005;


    @IntDef({LOCAL_UNKNOWN_ERROR, LOCAL_NO_NETWORK, LOCAL_TIMEOUT_ERROR, CODE_SUCCESS, CODE_ERROR,
            CODE_EMPTY, CODE_TOKEN_EXPIRE, CODE_TOKEN_INVALID, CODE_REQUEST_BUSY, CODE_SIGN_ERROR, CODE_SIGN_EMPTY,
            CODE_TIME_STAMP_EMPTY, CODE_TOKEN_EMPTY, CODE_KEY_ERROR, CODE_USER_ERROR, CODE_REQUEST_ERROR,
            WX_TOKEN_EXPIRE, WX_ILLEGAL, WX_ERROR_SIZE, WX_IMAGE_SIZE, WX_VOICE_SIZE, WX_VIDEO_SIZE, WX_THUMB_SIZE, WX_TYPE_ERROR,
            WX_MEDIA_ERROR, WX_FILE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Exception {
    }

}
