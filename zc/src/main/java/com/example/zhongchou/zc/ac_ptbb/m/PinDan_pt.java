package com.example.zhongchou.zc.ac_ptbb.m;


import com.example.zhongchou.zc.ac_staffSend.m.Staff_pt;

import java.io.Serializable;

/**
 * Created by zuoyun on 2017/10/24.
 */

public class PinDan_pt implements Serializable {
    public boolean checked;

    public String id;
    public String isNewRecord;
    public String createDate;
    public String orderId;
    public String actId;
    public double price;
    public String memberId;
    public String payOrderNumber;
    public String status;//1待成团 2已成团 3失败 ,
    public String goodNum;
    public String redeemCode;
    public String redeemCodeUrl;
    public String codeStatus;
    public String goodName;
    public String type; //1.微信支付 2会员支付 ,

    public Staff_pt user;
    public Member_pt member;

}
