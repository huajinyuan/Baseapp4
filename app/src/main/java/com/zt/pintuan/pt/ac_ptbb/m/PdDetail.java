package com.zt.pintuan.pt.ac_ptbb.m;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zuoyun on 2017/10/24.
 */

public class PdDetail implements Serializable {
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
    public int status;//1待成团 2已成团 3失败 ,
    public int count;
    public int groupNum;

    public Head_pt head;
    public User user;
    public ArrayList<Member_pt> members;

}
