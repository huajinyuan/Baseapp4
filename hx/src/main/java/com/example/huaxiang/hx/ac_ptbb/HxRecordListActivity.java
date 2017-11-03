package com.example.huaxiang.hx.ac_ptbb;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.adapter.HxRecordListAdapter;
import com.example.huaxiang.module.base.BaseActivity;

import java.util.ArrayList;

public class HxRecordListActivity extends BaseActivity<HxRecordListActivityPresenter> {

    RecyclerView rc_Record;
    LinearLayoutManager layoutManager;
    HxRecordListAdapter adapter;
    ArrayList<String> pinDan_pts = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hx_record_list;
    }

    @Override
    protected void initView() {
        rc_Record = findView(R.id.rv_hxRecord_content);


    }

    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            pinDan_pts.add("" + i);
        }
        setRv(pinDan_pts);
    }

    @Override
    protected void setListener() {

    }

    void setRv(ArrayList<String> pinDans) {
        if (adapter == null) {
            pinDan_pts.addAll(pinDans);
            adapter = new HxRecordListAdapter(this, pinDan_pts);
            layoutManager = new LinearLayoutManager(this);
            rc_Record.setLayoutManager(layoutManager);
            rc_Record.setAdapter(adapter);
        } else {
            pinDan_pts.addAll(pinDans);
            adapter.notifyDataSetChanged();
        }
    }
}
