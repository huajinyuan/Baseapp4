package com.example.huaxiang.hx.ac_bb.m;

import com.example.huaxiang.hx.ac_memberget.m.User;

import java.io.Serializable;

/**
 * Created by zuoyun on 2017/11/6.
 */

public class Record implements Serializable{
    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String visitId;
    public String content;

    public User user;

}
