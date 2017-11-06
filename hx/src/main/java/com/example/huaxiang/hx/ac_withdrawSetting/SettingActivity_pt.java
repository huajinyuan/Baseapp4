package com.example.huaxiang.hx.ac_withdrawSetting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.AppContext;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(SettingPresenter_pt.class)
public class SettingActivity_pt extends BaseActivity<SettingPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    CheckBox cb_persent;
    CheckBox cb_num;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_hx;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("提成设置");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("提现");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        cb_persent = (CheckBox) findViewById(R.id.cb_persent);
        cb_num = (CheckBox) findViewById(R.id.cb_num);

    }

    @Override
    protected void initData() {
    }


    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.rl_persent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCB();
                cb_persent.setChecked(true);
            }
        });
        findViewById(R.id.rl_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCB();
                cb_num.setChecked(true);
            }
        });
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AccountListActivity_cj.class));
            }
        });
    }

    private void clearCB(){
        cb_persent.setChecked(false);
        cb_num.setChecked(false);
    }


}
