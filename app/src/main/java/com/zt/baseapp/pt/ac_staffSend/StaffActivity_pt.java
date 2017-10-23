package com.zt.baseapp.pt.ac_staffSend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.pt.ac_staffSend.adapter.StaffAdapter_pt;
import com.zt.baseapp.pt.ac_staffSend.m.Staff_pt;
import com.zt.baseapp.utils.ACache;
import com.zt.baseapp.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(StaffPresenter_pt.class)
public class StaffActivity_pt extends BaseActivity<StaffPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_pt;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("员工管理");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("门店管理");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

    }

    @Override
    protected void initData() {
        ArrayList<Staff_pt> staffs = new ArrayList<>();
        for (int i=0;i<10;i++) {
            Staff_pt staff = new Staff_pt();
            staff.name = "张滨华";
            staff.storeName = "众途" + i + "号店";
            staffs.add(staff);
        }
        StaffAdapter_pt adapter = new StaffAdapter_pt(context, staffs);

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
                context.startActivity(new Intent(context, ShopActivity_pt.class));
            }
        });
        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddStaffActivity_pt.class));
            }
        });
    }

//    void login(){
//        HttpMethods.getInstance().login("shanghu2", "123456").subscribe(new Subscriber<Response<LoginData_pt>>(){
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                Log.e("=============", "onStart");
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.e("=============", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("=======onError", e.toString() + "");
//            }
//
//            @Override
//            public void onNext(Response<LoginData_pt> logdResponse) {
//                if (logdResponse.code==0){
//                    aCache.put(ACacheKey.TOKEN, logdResponse.data.getToken());
//                    token = logdResponse.data.getToken();
//                    getReport_pt();
//                    Log.e("aaa========Token:", token);
//
//                }else {
//                    Log.e("=======onNext", logdResponse.msg);
//                }
//            }
//        });
//    }



}
