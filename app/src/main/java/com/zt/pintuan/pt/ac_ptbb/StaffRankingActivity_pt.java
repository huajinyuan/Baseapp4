package com.zt.pintuan.pt.ac_ptbb;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.model.Response;
import com.zt.pintuan.module.base.BaseActivity;
import com.zt.pintuan.network.retrofit.HttpMethods;
import com.zt.pintuan.pt.ac_ptbb.adapter.StaffRankingAdapter_pt;
import com.zt.pintuan.pt.ac_staffSend.m.Staff_pt;
import com.zt.pintuan.utils.ACache;
import com.zt.pintuan.utils.ACacheKey;

import java.util.ArrayList;

import rx.Subscriber;


public class StaffRankingActivity_pt extends BaseActivity<StaffRankingPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    String actId;
    RecyclerView rv_staffSend;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_ranking_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("员工排行");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);
    }

    void setRv(ArrayList<Staff_pt> staff_pts) {
        StaffRankingAdapter_pt adapter = new StaffRankingAdapter_pt(context, staff_pts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_staffSend.setLayoutManager(layoutManager);
        rv_staffSend.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        actId = getIntent().getStringExtra("actId");
        getData();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void getData(){
        if (actId == null) {
            HttpMethods.start(HttpMethods.getInstance().demoService.getStaffRanking_pt(token, 1, 100, 0), new Subscriber<Response<ArrayList<Staff_pt>>>() {
                @Override
                public void onCompleted() {
                    Log.e("aaa", "onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("aaa", "onError" + e.getMessage());
                }

                @Override
                public void onNext(Response<ArrayList<Staff_pt>> arrayListResponse) {
                    if (arrayListResponse.data != null) {
                        setRv(arrayListResponse.data);
                    }
                }
            });
        } else {
            HttpMethods.start(HttpMethods.getInstance().demoService.getStaffRanking_pt(token, 1, 100, 0, actId), new Subscriber<Response<ArrayList<Staff_pt>>>() {
                @Override
                public void onCompleted() {
                    Log.e("aaa", "onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("aaa", "onError" + e.getMessage());
                }

                @Override
                public void onNext(Response<ArrayList<Staff_pt>> arrayListResponse) {
                    if (arrayListResponse.data != null) {
                        setRv(arrayListResponse.data);
                    }
                }
            });
        }
    }

}
