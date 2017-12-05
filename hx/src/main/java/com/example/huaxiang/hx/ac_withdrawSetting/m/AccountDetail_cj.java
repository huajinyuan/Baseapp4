package com.example.huaxiang.hx.ac_withdrawSetting.m;

/**
 * Created by zuoyun on 2017/10/29.
 */

public class AccountDetail_cj {
    public String id ;
    public String staffId ;
    public double begin ;
    public double end;
    public int type; //类型 1支出 2收入 ,
    public int billType; //账单类型 1提现 2拼团奖励 3其他 ,
    public double amount;
    public double balance ;
    public double totalWithdrawals ;

    public String createDate;
    public String updateDate ;
    public String isNewRecord ;
}
