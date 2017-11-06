package com.example.huaxiang.hx.ac_ptList;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(AcListPresenter_pt.class)
public class AcInfoActivity_pt extends BaseActivity<AcInfoPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ac_info_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("活动详情");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("编辑");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
    }

    @Override
    protected void setListener() {
        findView(R.id.img_topbar_back).setOnClickListener(view -> finish());
    }
}
