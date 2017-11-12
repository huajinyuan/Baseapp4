package com.zt.pintuan.model;

import com.google.gson.annotations.SerializedName;
import com.zt.pintuan.base.ReturnCode;

import java.io.Serializable;
import java.util.List;

/**
 * <网络请求返回实体类>
 */
public class Response<T> implements Serializable {

    @SerializedName("code")
    public int code;

    @SerializedName("msg")
    public String msg;

    @SerializedName("data")
    public T data;

    public Response(@ReturnCode.Exception int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return code == ReturnCode.CODE_SUCCESS;
    }

    public boolean isListEmpty() {
        return data == null || (data instanceof List && ((List) data).size() == 0);
    }
}
