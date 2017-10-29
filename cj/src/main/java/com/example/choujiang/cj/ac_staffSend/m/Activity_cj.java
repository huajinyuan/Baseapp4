package com.example.choujiang.cj.ac_staffSend.m;

import java.io.Serializable;

/**
 * Created by zuoyun on 2017/10/22.
 */

public class Activity_cj implements Serializable{
    public boolean checked = false;
    public int totalManCount;
    public int totalShareCount;
    public int actId;
    public int totalCount;
    public int totalAwardCount;
    public String actName;

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
    public int count;
    public int shareCount;
    public int exchangeCount;
    public int status;//1可用 2暂停 3作废 ,
}
