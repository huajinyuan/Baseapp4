package com.zt.baseapp.network.exception;


import com.zt.baseapp.base.ReturnCode;

public class ErrorThrowable extends Throwable {
    public int code;
    public String msg;

    public ErrorThrowable(@ReturnCode.Exception int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static ErrorThrowable convertErr(Throwable throwable) {
        if (throwable == null) return new ErrorThrowable(ReturnCode.CODE_ERROR, "未知错误");
        if (throwable instanceof ErrorThrowable) {
            return (ErrorThrowable) throwable;
        } else {
            return new ErrorThrowable(ReturnCode.CODE_ERROR, throwable.toString());
        }
    }
}
