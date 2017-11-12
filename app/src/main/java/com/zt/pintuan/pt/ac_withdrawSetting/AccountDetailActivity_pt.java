package com.zt.pintuan.pt.ac_withdrawSetting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.module.base.BaseActivity;
import com.zt.pintuan.pt.ac_withdrawSetting.adapter.AccountDetailAdapter_pt;
import com.zt.pintuan.pt.ac_withdrawSetting.m.AccountDetail_pt;
import com.zt.pintuan.utils.ACache;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(AccountDetailPresenter_pt.class)
public class AccountDetailActivity_pt extends BaseActivity<AccountDetailPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_accountDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_detail_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("员工账户");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("提现");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_accountDetail = (RecyclerView) findViewById(R.id.rv_accountDetail);

    }

    @Override
    protected void initData() {
        ArrayList<AccountDetail_pt> accountDetails = new ArrayList<>();
        AccountDetail_pt accountDetail = new AccountDetail_pt();
        accountDetail.change = "5";
        accountDetail.type = "拼团奖励";
        accountDetail.balance = "1000";
        accountDetail.date = "2017/08/11";
        for(int i=0;i<10;i++) {
            accountDetails.add(accountDetail);
        }
        AccountDetailAdapter_pt adapter = new AccountDetailAdapter_pt(context, accountDetails);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_accountDetail.setLayoutManager(layoutManager);
        rv_accountDetail.setAdapter(adapter);
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
                context.startActivity(new Intent(context, WithdrawActivity_pt.class));
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
//                    getReport();
//                    Log.e("aaa========Token:", token);
//
//                }else {
//                    Log.e("=======onNext", logdResponse.msg);
//                }
//            }
//        });
//    }



}
