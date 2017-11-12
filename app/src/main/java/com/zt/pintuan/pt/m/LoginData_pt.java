package com.zt.pintuan.pt.m;

import com.zt.pintuan.model.BaseInfo;

import java.io.Serializable;

/**
 * Created by gtgs on 17/10/17.
 */

public class LoginData_pt extends BaseInfo implements Serializable {

    private String userId;
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return userId + "_" + token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginData_pt{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
