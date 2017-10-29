package com.example.choujiang.cj.ac_staffSend.staffDetail;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_staffSend.m.Activity_cj;
import com.example.choujiang.cj.ac_staffSend.staffDetail.adapter.StaffDetailAdapter_pt;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;
import com.example.choujiang.utils.AppContext;

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

    StaffDetailAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Activity_cj> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;

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
        getData();

        rv_staffSend.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    void setRv(ArrayList<Activity_cj> pinDans) {
        if (adapter == null) {
            pinDan_pts.addAll(pinDans);
            adapter = new StaffDetailAdapter_pt(context, pinDan_pts);
            layoutManager = new LinearLayoutManager(context);
            rv_staffSend.setLayoutManager(layoutManager);
            rv_staffSend.setAdapter(adapter);
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

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getStaffAcDetail_cj(token, page, 100, userId), new Subscriber<Response<ArrayList<Activity_cj>>>() {
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
            public void onNext(Response<ArrayList<Activity_cj>> arrayListResponse) {
                setRv(arrayListResponse.data);
                canGet = true;
                page++;
            }
        });

    }


}
