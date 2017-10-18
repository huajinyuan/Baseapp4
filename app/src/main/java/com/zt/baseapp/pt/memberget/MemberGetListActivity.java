package com.zt.baseapp.pt.memberget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.pt.adapter.MemberGetlistAdapter;



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
