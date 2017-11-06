package com.example.huaxiang.hx.ac_withdrawSetting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_staffSend.m.Staff_cj;
import com.example.huaxiang.hx.ac_withdrawSetting.adapter.AccountListAdapter_cj;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(AccountListPresenter_cj.class)
public class AccountListActivity_cj extends BaseActivity<AccountListPresenter_cj> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_accountList;
    AccountListAdapter_cj adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Staff_cj> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_list_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("账户列表");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("筛选");
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        rv_accountList = (RecyclerView) findViewById(R.id.rv_accountList);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        getData();
    }

    void setRv(ArrayList<Staff_cj> pinDans) {
        pinDan_pts = pinDans;
        adapter = new AccountListAdapter_cj(context, pinDan_pts);
        layoutManager = new LinearLayoutManager(context);
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
