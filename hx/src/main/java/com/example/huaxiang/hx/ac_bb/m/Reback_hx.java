package com.example.huaxiang.hx.ac_bb.m;

import com.example.huaxiang.hx.ac_memberget.m.Member;
import com.example.huaxiang.hx.ac_memberget.m.User;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zuoyun on 2017/11/6.
 */

public class Reback_hx implements Serializable{
    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String actId;
    public String merchantId;
    public String memberId;
    public String licensePlate;
    public String type;// 0全部 1意向客户 2 转化客户 3无意向
    public String phone;
    public String actName;
    public String awardName;

    public Member member;
    public User user;
    public ArrayList<Record> recordList;

}
