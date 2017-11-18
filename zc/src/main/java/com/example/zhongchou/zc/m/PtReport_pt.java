package com.example.zhongchou.zc.m;


import com.example.zhongchou.model.BaseInfo;

import java.io.Serializable;

/**
 * Created by gtgs on 17/10/18.
 */

public class PtReport_pt extends BaseInfo implements Serializable {
    public double drivingTurnover;
    public int orderCount;
    public int completedCount;
    public int awaitCount;
}
