package com.zt.baseapp.pt.ac_ptbb.m;

import com.zt.baseapp.pt.ac_staffSend.m.Staff_pt;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zuoyun on 2017/10/24.
 */

public class PinTuan_pt implements Serializable {
    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String actId;
    public String actName;
    public double price;
    public String headId;
    public String goodId;

    public String orderNumber;
    public String status;//1待成团 2已成团 3失败 ,
    public String count;
    public String groupNum;
    public String actualCount;
    public String totalPrice;

    public Head_pt head;
    public Staff_pt user;
    public ArrayList<Member_pt> members;

}
