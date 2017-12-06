package com.example.huaxiang.model;

import com.example.huaxiang.base.ReturnCode;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * <网络请求返回实体类>
 */
public class Response<T> implements Serializable {

    @SerializedName("code")
    public int code;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public T data;

    public Response(@ReturnCode.Exception int code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return code == ReturnCode.CODE_SUCCESS;
    }

    public boolean isListEmpty() {
        return data == null || (data instanceof List && ((List) data).size() == 0);
    }
}
