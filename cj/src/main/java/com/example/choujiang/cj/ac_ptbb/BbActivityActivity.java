package com.example.choujiang.cj.ac_ptbb;

import android.widget.ListView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.adapter.AcBbAdapter;
import com.example.choujiang.module.base.BaseActivity;


public class BbActivityActivity extends BaseActivity<BbActivityPresenter> {

    ListView mLv;
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bb_activity;
    }

    @Override
    protected void initView() {

        tvTitle = (TextView) findViewById(R.id.tv_topbar_title);
        mLv = (ListView) findViewById(R.id.lv_content);

    }

    @Override
    protected void initData() {
        tvTitle.setText("活动报表");
        mLv.setAdapter(new AcBbAdapter(this));
    }

    @Override
    protected void setListener() {
        findView(R.id.img_topbar_back).setOnClickListener(view -> finish());
    }

}
