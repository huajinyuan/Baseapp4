package com.example.huaxiang.hx.ac_staffSend.m;

import java.io.Serializable;

/**
 * Created by zuoyun on 2017/10/22.
 */

public class Activity_cj implements Serializable {
    public boolean checked = false;
    public int conversionCount;
    public int replaceNum;
    public int intentionCount;
    public int totalNum;
    public int actId;
    public String lotteryNumber;


    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String merchantId;
    public String storeId;
    public String name;
    public String imgUrl;
    public String beginTime;
    public String endTime;
    public String remarks;
    public int num;
    public int saleNum;
    public int surplus;
    public double money;
    public int status;//1可用 2暂停 3作废 ,
    public int carCheck;
    public int replaceTime;
}