package com.zt.baseapp.pt.ac_staffSend.staffDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.model.Response;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.network.retrofit.HttpMethods;
import com.zt.baseapp.pt.ac_staffSend.StaffActivity_pt;
import com.zt.baseapp.pt.ac_staffSend.m.Activity_pt;
import com.zt.baseapp.pt.ac_staffSend.staffDetail.adapter.StaffDetailAdapter_pt;
import com.zt.baseapp.utils.ACache;
import com.zt.baseapp.utils.ACacheKey;
import com.zt.baseapp.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(StaffDetailPresenter_pt.class)
public class StaffDetailActivity_pt extends BaseActivity<StaffDetailPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    ImageView iv_qr_bottom;
    int userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_detail_pt;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("员工二维码");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("设置");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);
        iv_qr_bottom = (ImageView) findViewById(R.id.iv_qr_bottom);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        userId = getIntent().getIntExtra("userId", 0);
        getStaffDetails();

    }

    void setRv(ArrayList<Activity_pt> activity_pts) {
        StaffDetailAdapter_pt adapter = new StaffDetailAdapter_pt(context, activity_pts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
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
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffActivity_pt.class));
            }
        });
        findViewById(R.id.iv_qr_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QrPopWin_pt popWin_pt = new QrPopWin_pt(context);
                popWin_pt.showAtLocation(findViewById(R.id.staff_detail_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    void getStaffDetails(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getStaffDetail_pt(token, 1, 100, 0, userId), new Subscriber<Response<ArrayList<Activity_pt>>>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<ArrayList<Activity_pt>> arrayListResponse) {
                setRv(arrayListResponse.data);
            }
        });
    }


}
