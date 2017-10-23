package com.zt.baseapp.pt.ac_ptbb;

import android.widget.ListView;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.pt.ac_ptbb.adapter.PdlistAdapter;


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
