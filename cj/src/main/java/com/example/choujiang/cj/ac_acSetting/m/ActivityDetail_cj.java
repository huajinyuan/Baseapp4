package com.example.choujiang.cj.ac_acSetting.m;

import com.example.choujiang.cj.ac_cjbb.m.CjHistory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zuoyun on 2017/10/29.
 */

public class ActivityDetail_cj implements Serializable{
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

    public ArrayList<Award> awards;
    public ArrayList<CjHistory> awardDetails;
}
