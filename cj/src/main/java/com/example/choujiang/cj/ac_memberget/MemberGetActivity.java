package com.example.choujiang.cj.ac_memberget;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.module.base.BaseActivity;


public class MemberGetActivity extends BaseActivity<MemberGetPresenter> {
    TextView tvTitle;
    TextView tvRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_get;
    }

    @Override
    protected void initView() {
        tvTitle = findView(R.id.tv_topbar_title);
        tvRight = findView(R.id.tv_topbar_right);
    }

    @Override
    protected void initData() {
        tvTitle.setText("客户兑换");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("记录");

    }

    @Override
    protected void setListener() {

        findView(R.id.img_topbar_back).setOnClickListener(view -> finish());
        findView(R.id.tv_topbar_right).setOnClickListener(view -> click2Intent(view));
    }

    public void click2Intent(View v) {
        Intent intent = new Intent(this, MemberGetListActivity.class);
        startActivity(intent);
    }
}
