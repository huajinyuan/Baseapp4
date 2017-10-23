package com.example.huaxiang.hx.ac_memberget;

import android.widget.ListView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.adapter.MemberGetlistAdapter;
import com.example.huaxiang.module.base.BaseActivity;


public class MemberGetListActivity extends BaseActivity<MemberGetListPresenter> {

    ListView mLv;
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_get_list;
    }

    @Override
    protected void initView() {

        mLv = findView(R.id.lv_content);
        tvTitle = findView(R.id.tv_topbar_title);
    }

    @Override
    protected void initData() {
        mLv.setAdapter(new MemberGetlistAdapter(this));
        tvTitle.setText("兑换记录");
    }

    @Override
    protected void setListener() {

        findView(R.id.img_topbar_back).setOnClickListener(view -> finish());
    }
}
