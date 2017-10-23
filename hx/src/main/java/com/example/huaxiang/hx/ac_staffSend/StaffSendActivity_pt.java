package com.example.huaxiang.hx.ac_staffSend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_staffSend.adapter.StaffSendAdapter_pt;
import com.example.huaxiang.hx.ac_staffSend.addStaffSend.SelectStaffActivity_pt;
import com.example.huaxiang.hx.ac_staffSend.m.StaffSend_pt;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;
import com.example.huaxiang.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(StaffSendPresenter_pt.class)
public class StaffSendActivity_pt extends BaseActivity<StaffSendPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_send_pt;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("员工发送");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("员工管理");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        getStaffSends();

    }

    void setRv(ArrayList<StaffSend_pt> staffSend_pts) {
        StaffSendAdapter_pt adapter = new StaffSendAdapter_pt(context, staffSend_pts);
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
        findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SelectStaffActivity_pt.class));
            }
        });
    }
    void getStaffSends(){
        HttpMethods.getInstance().getStaffSends(token, 1, 10).subscribe(new Subscriber<Response<ArrayList<StaffSend_pt>>>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.e("=============", "onStart");
            }

            @Override
            public void onCompleted() {
                Log.e("=============", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("=======onError", e.toString() + "");
            }

            @Override
            public void onNext(Response<ArrayList<StaffSend_pt>> logdResponse) {
                if (logdResponse.code == 0) {
                    Log.e("aaa", logdResponse.data.get(0).createDate);
                    setRv(logdResponse.data);
                } else {
                    Log.e("=======onNext", logdResponse.msg);
                }
            }
        });
    }


}
