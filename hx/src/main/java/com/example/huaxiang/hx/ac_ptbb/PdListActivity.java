package com.example.huaxiang.hx.ac_ptbb;

import android.widget.ListView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.adapter.PdlistAdapter;
import com.example.huaxiang.module.base.BaseActivity;


public class PdListActivity extends BaseActivity<PdListPresenter> {
    ListView mLv;
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pt_list;
    }

    @Override
    protected void initView() {
        tvTitle = findView(R.id.tv_topbar_title);
        mLv = findView(R.id.lv_content);

    }

    @Override
    protected void initData() {
        mLv.setAdapter(new PdlistAdapter(this));
        tvTitle.setText("清单列表");
    }

    @Override
    protected void setListener() {
        findView(R.id.img_topbar_back).setOnClickListener(view -> finish());
    }

}