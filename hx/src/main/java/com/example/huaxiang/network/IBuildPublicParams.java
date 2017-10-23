package com.example.huaxiang.network;

import okhttp3.Request;

/**
 * Created by caiyk on 2017/9/11.
 */

public interface IBuildPublicParams {
    //构建公共参数
    Request.Builder buildPublicParams(Request.Builder builder);
}
