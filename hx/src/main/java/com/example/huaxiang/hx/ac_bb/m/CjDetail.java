package com.example.huaxiang.hx.ac_bb.m;

import com.example.huaxiang.hx.ac_memberget.m.CjHistory;
import com.example.huaxiang.hx.ac_memberget.m.Member;
import com.example.huaxiang.hx.ac_memberget.m.User;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zuoyun on 2017/11/5.
 */

public class CjDetail implements Serializable {
    public boolean uploaded = false;
    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String merchantId;
    public String actId;
    public String number;

    public String memberId;
    public int replaceTime;
    public int replaced;
    public int payStatus;//0未支付 1已支付 2待拆奖 3已拆奖
    public String isAward;
    public double money;
    public String actName;
    public String memberName;
    public String phone;
    public String licensePlate;
    public String awardName;


    public User user;
    public Member member;
    public ArrayList<CjHistory> hxAwardDetails;

}