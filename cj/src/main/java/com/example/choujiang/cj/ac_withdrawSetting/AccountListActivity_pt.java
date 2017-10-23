package com.example.choujiang.cj.ac_withdrawSetting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.choujiang.R;
import com.example.choujiang.cj.ac_withdrawSetting.adapter.AccountListAdapter_pt;
import com.example.choujiang.cj.ac_withdrawSetting.m.Account_pt;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(AccountListPresenter_pt.class)
public class AccountListActivity_pt extends BaseActivity<AccountListPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_accountList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_list_pt;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("账户列表");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("筛选");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_accountList = (RecyclerView) findViewById(R.id.rv_accountList);

    }

    @Override
    protected void initData() {
        ArrayList<Account_pt> accounts = new ArrayList<>();
        Account_pt account = new Account_pt();
        account.name = "张滨华";
        account.commission = "1000";
        account.balance = "590";
        for (int i=0;i<10;i++) {
            accounts.add(account);
        }
        AccountListAdapter_pt adapter = new AccountListAdapter_pt(context, accounts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_accountList.setLayoutManager(layoutManager);
        rv_accountList.setAdapter(adapter);
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
                context.startActivity(new Intent(context, AccountDetailActivity_pt.class));
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
