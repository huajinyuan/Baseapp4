package com.example.huaxiang.hx.ac_bb.m;

import java.io.Serializable;

/**
 * Created by zuoyun on 2017/11/6.
 */

public class HxTopic implements Serializable {
    public String id;
    public String isNewRecord;
    public String createDate;
    public String updateDate;
    public String actId;
    public String question;
    public int type;//1单选2多选
    public String option;
    public String answer;

    public HxTopicMember hxTopicMember;

}
