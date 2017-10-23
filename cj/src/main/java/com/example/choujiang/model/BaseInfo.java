package com.example.choujiang.model;

import java.io.Serializable;

/**
 * <基础类>
 */
public abstract class BaseInfo implements Serializable {
    //实体类返回提示语
    private String successHintMsg;

    public void setSuccessHintMsg(String msg) {
        this.successHintMsg = msg;
    }

    public String getSuccessHintMsg() {
        return successHintMsg;
    }
}
