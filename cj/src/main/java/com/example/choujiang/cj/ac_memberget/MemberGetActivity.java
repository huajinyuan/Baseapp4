package com.example.choujiang.cj.ac_memberget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;


public class MemberGetActivity extends BaseActivity<MemberGetPresenter> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    EditText et_code;
    TextView tv_status;
    TextView tv_personName;
    TextView tv_goodsName;
    TextView tv_goodsNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_get_cj;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("立即兑换");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("记录");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        et_code = findView(R.id.et_code);
        tv_status = findView(R.id.tv_status);
        tv_personName = findView(R.id.tv_personName);
        tv_goodsName = findView(R.id.tv_goodsName);
        tv_goodsNum = findView(R.id.tv_goodsNum);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);

    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(view -> finish());
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MemberGetListActivity.class));
            }
        });

        findViewById(R.id.iv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
