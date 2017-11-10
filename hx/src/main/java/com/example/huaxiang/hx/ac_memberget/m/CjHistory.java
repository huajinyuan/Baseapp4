package com.example.huaxiang.hx.ac_memberget.m;

import java.io.Serializable;

/**
 * Created by zuoyun on 2017/11/5.
 */

public class CjHistory implements Serializable {
    public boolean uploaded = false;
    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String number;
    public String merchantId;
    public String actId;
    public String orderId;

    public String memberId;
    public String replaceMemberId;
    public String isAward;
    public String awardId;
    public String redeemCode;
    public String codeStatus; //0 未兑换 1已兑换
    public String redeemCodeUrl;
    public int type;
    public double price;
    public String imgUrl;
    public String awardName;
    public String ableTime;
    public String awardNum;

    public String mobile;



    public User user;
    public Member member;

}