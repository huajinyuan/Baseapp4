package com.zt.baseapp.network;


import com.zt.baseapp.utils.ConstUtils;
import com.zt.baseapp.utils.LogUtil;
import com.zt.baseapp.utils.MD5Tool;
import com.zt.baseapp.utils.TimeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by caiyk on 2017/2/27.
 */

public class PublicParamsInterceptor implements Interceptor {
    public static long timeSpan = 0;
    private static IBuildPublicParams iBuildPublicParams;

    public PublicParamsInterceptor(IBuildPublicParams iBuildPublicParams) {
        this.iBuildPublicParams = iBuildPublicParams;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("GET")) {
            request = addGetParams(request);
        }
        if (request.method().equals("POST")) {
            request = addPostParams(request);
        }
        Request.Builder requestBuilder = request.newBuilder();
        request = iBuildPublicParams == null ? requestBuilder.build() : iBuildPublicParams.buildPublicParams(requestBuilder).build();
        Response response = chain.proceed(request);
        long serverTimeStamp = TimeUtils.transformGMT(response.header("Date"));
        timeSpan = TimeUtils.getTimeSpanByNow(serverTimeStamp, ConstUtils.TimeUnit.MSEC);
        timeSpan = TimeUtils.getNowTimeMills() > serverTimeStamp ? -timeSpan : timeSpan;
        LogUtil.d("服务器时间: " + response.header("Date"));
        return response;
    }

    private Request addPostParams(Request request) {
        if (request.body() instanceof FormBody) {
            long serverTimeStamp = TimeUtils.getServerTimeStamp(timeSpan);
            FormBody formBody = (FormBody) request.body();
            Map<String, String> bodyMap = new HashMap<>();
            List<String> nameList = new ArrayList<>();
            for (int i = 0; i < formBody.size(); i++) {
                nameList.add(formBody.name(i));
                bodyMap.put(formBody.name(i), formBody.value(i));
            }
            Collections.sort(nameList);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < nameList.size(); i++) {
                builder.append(nameList.get(i))
                        .append(bodyMap.get(nameList.get(i)));
            }
            builder.append("timeStamp").append(serverTimeStamp);
            LogUtil.d(builder.toString());
            request = request.newBuilder().addHeader("sign", MD5Tool.MD5(builder.toString().trim()).toUpperCase())
                    .addHeader("timeStamp", String.valueOf(serverTimeStamp)).build();
        }
        return request;
    }

    private Request addGetParams(Request request) {
        long serverTimeStamp = TimeUtils.getServerTimeStamp(timeSpan);
        HttpUrl httpUrl = request.url().newBuilder().build();
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> signList = new ArrayList<>();
        signList.addAll(nameSet);
        Collections.sort(signList);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < signList.size(); i++) {
            builder.append(signList.get(i))
                    .append(httpUrl.queryParameterValues(signList.get(i)) != null && httpUrl.queryParameterValues(signList.get(i)).size() > 0 ?
                            httpUrl.queryParameterValues(signList.get(i)).get(0) : "");
        }
        builder.append("timeStamp").append(TimeUtils.getServerTimeStamp(timeSpan));
        LogUtil.d(builder.toString());
        request = request.newBuilder().addHeader("sign", MD5Tool.MD5(builder.toString().trim()).toUpperCase())
                .addHeader("timeStamp", String.valueOf(serverTimeStamp)).build();
        return request;
    }
}
