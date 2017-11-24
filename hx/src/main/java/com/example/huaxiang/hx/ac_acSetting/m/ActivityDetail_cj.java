package com.example.huaxiang.hx.ac_acSetting.m;

import com.example.huaxiang.hx.ac_bb.m.HxTopic;
import com.example.huaxiang.hx.ac_memberget.m.CjHistory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zuoyun on 2017/10/29.
 */

public class ActivityDetail_cj implements Serializable{
    public boolean checked = false;

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
    public String videoUrl;
    public int num;
    public int saleNum;
    public int carCheck;
    public int replaceTime;
    public int status;//1可用 2暂停 3作废 ,
    public double money;

    public ArrayList<Award> awards;
    public Award award;
    public ArrayList<CjHistory> details;
    public ArrayList<HxTopic> topics;

}
