package com.zt.baseapp.pt.model;

import com.zt.baseapp.model.BaseInfo;

import java.io.Serializable;

/**
 * Created by gtgs on 17/10/17.
 */

public class Logd extends BaseInfo implements Serializable {

    private String userId;
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
