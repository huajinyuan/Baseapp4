package com.zt.baseapp.pt.ac_staffSend.m;

import java.io.Serializable;

/**
 * Created by zuoyun on 2017/10/22.
 */

public class Activity_pt implements Serializable {
    public boolean checked = false;
    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String merchantId;
    public String merchantAddress;
    public String goodId;
    public String storeId;
    public String storeName;
    public String storePhone;
    public String name;
    public String imgUrl;
    public String beginTime;
    public String endTime;
    public int num;
    public String saleNum;
    public String surplus;
    public String saleRemarks;
    public String status;
    public Goods_pt ptGood;

    public String drivingTurnover;
    public String groupSize;
    public String cliqueNumber;
    public String spellTogether;
}
