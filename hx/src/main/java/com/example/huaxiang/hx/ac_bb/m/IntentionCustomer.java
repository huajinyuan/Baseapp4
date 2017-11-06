package com.example.huaxiang.hx.ac_bb.m;

import com.example.huaxiang.hx.ac_memberget.m.Member;
import com.example.huaxiang.hx.ac_memberget.m.User;

import java.io.Serializable;

/**
 * Created by zuoyun on 2017/11/5.
 */

public class IntentionCustomer implements Serializable {
    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String merchantId;
    public String actId;
    public String number;
    public String memberId;
    public int replaceTime;
    public int replaced ;
    public int payStatus ;
    public String isAward;
    public double money;
    public String actName;
    public String memberName;
    public String phone;

    User user;
    Member member;

}
