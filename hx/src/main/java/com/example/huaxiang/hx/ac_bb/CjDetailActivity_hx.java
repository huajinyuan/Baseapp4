package com.example.huaxiang.hx.ac_bb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_staffSend.addStaffSend.adapter.SelectStaffAdapter_pt;
import com.example.huaxiang.hx.ac_staffSend.m.Staff_cj;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;
import com.example.huaxiang.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(CjDetailPresenter_hx.class)
public class CjDetailActivity_hx extends BaseActivity<CjDetailPresenter_hx> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    SelectStaffAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Staff_cj> pinDan_pts = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cj_detail;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("抽奖记录");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
//        getData();
    }

    void setRv(ArrayList<Staff_cj> pinDans) {
        pinDan_pts = pinDans;
        adapter = new SelectStaffAdapter_pt(context, pinDan_pts);
        layoutManager = new LinearLayoutManager(context);
        rv_staffSend.setLayoutManager(layoutManager);
        rv_staffSend.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.ll_topic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int actId = getIntent().getIntExtra("actId", 0);
                startActivity(new Intent(context, CjDetailTopicActivity_pt.class).putExtra("actId", actId));
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getStaff_cj(token), new Subscriber<Response<ArrayList<Staff_cj>>>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<ArrayList<Staff_cj>> arrayListResponse) {
                if (arrayListResponse != null) {
                    setRv(arrayListResponse.data);
                }
            }
        });

    }

}
