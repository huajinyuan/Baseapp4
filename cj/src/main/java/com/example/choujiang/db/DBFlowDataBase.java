package com.example.choujiang.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by caiyk on 2017/6/14.
 */
@Database(name = DBFlowDataBase.NAME, version = DBFlowDataBase.VERSION)
public class DBFlowDataBase {
    //数据库名称
    public static final String NAME = "ZTDataBase";
    //数据库版本
    public static final int VERSION = 1;
}
