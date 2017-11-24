package com.example.huaxiang.hx.m;


import com.example.huaxiang.model.BaseInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gtgs on 17/10/17.
 */

public class LoginData_pt extends BaseInfo implements Serializable {

    private String userId;
    private String token;
    public ArrayList<AppMenu> menuList;

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
