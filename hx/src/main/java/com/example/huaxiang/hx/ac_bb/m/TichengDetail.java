package com.example.huaxiang.hx.ac_bb.m;

import com.example.huaxiang.hx.ac_memberget.m.Member;
import com.example.huaxiang.hx.ac_memberget.m.User;

import java.io.Serializable;

/**
 * Created by zuoyun on 2017/11/5.
 */

public class TichengDetail implements Serializable {
    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String merchantId;
    public String actId;
    public String number;
    public String memberId;
    public String userName;
    public String actName;
    public double commission;
    public double actPrice;

    public User user;
    public Member member;

}
