package com.example.zhongchou.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by caiyk on 2017/6/14.
 */
@Table(database = DBFlowDataBase.class)
public class TestData extends BaseModel {
    @PrimaryKey(autoincrement = true) //主键  //autoincrement 开启自增
    public int id;
    @Column
    public String name;
    @Column
    public int age;
    @Column
    public String address;
    @Column
    public int phone;
}
