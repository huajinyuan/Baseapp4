package com.example.choujiang.cj.ac_withdrawSetting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_staffSend.m.AccountDetail_cj;
import com.example.choujiang.cj.ac_staffSend.m.Staff_cj;
import com.example.choujiang.cj.ac_withdrawSetting.adapter.AccountDetailAdapter_cj;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(AccountDetailPresenter_pt.class)
public class AccountDetailActivity_pt extends BaseActivity<AccountDetailPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    TextView tv_name;
    TextView tv_totalWithdraw;
    TextView tv_balance;

    Staff_cj staff_cj;
    RecyclerView rv_accountDetail;
    AccountDetailAdapter_cj adapter;
    LinearLayoutManager layoutManager;
    ArrayList<AccountDetail_cj> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;
    public static AccountDetailActivity_pt instance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_detail_cj;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        instance = this;
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("员工账户");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("提现");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_accountDetail = (RecyclerView) findViewById(R.id.rv_accountDetail);

        tv_name = findView(R.id.tv_name);
        tv_totalWithdraw = findView(R.id.tv_totalWithdraw);
        tv_balance = findView(R.id.tv_balance);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        staff_cj = (Staff_cj) getIntent().getSerializableExtra("staff");
        if (staff_cj != null) {
            setData();
            getData();

            rv_accountDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE)
                            if(canGet)
                                getData();
                }
            });
        }
    }

    void setData(){
        tv_name.setText(staff_cj.name);
        tv_totalWithdraw.setText("￥" + staff_cj.totalWithdrawals);
        tv_balance.setText("￥" + staff_cj.balance);
    }
    void setRv(ArrayList<AccountDetail_cj> pinDans) {
        if (adapter == null) {
            pinDan_pts.clear();
            pinDan_pts.addAll(pinDans);
            adapter = new AccountDetailAdapter_cj(context, pinDan_pts);
            layoutManager = new LinearLayoutManager(context);
            rv_accountDetail.setLayoutManager(layoutManager);
            rv_accountDetail.setAdapter(adapter);
        } else {
            pinDan_pts.addAll(pinDans);
            adapter.notifyDataSetChanged();
        }
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
                context.startActivity(new Intent(context, WithdrawActivity_pt.class).putExtra("staff", staff_cj));
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getAccountDetail(token, staff_cj.id,page,10), new Subscriber<Response<ArrayList<AccountDetail_cj>>>() {
            @Override
            public void onStart() {
                super.onStart();
                canGet = false;
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
            public void onNext(Response<ArrayList<AccountDetail_cj>> arrayListResponse) {
                setRv(arrayListResponse.data);
                canGet = true;
                page++;
            }
        });

    }
    public void refresh(){
        page = 1;
        adapter = null;
        getData();
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
