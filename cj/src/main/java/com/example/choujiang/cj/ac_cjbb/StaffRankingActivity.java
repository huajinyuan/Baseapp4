package com.example.choujiang.cj.ac_cjbb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_cjbb.adapter.StaffRankingAdapter;
import com.example.choujiang.cj.ac_staffSend.m.Staff_cj;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;

import java.util.ArrayList;

import rx.Subscriber;


public class StaffRankingActivity extends BaseActivity<StaffRankingPresenter> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;
    ImageView iv_topbar_right_detail;

    String actId;
    RecyclerView rv_staffSend;
    StaffRankingAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Staff_cj> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_ranking_cj;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        iv_topbar_right_detail = (ImageView) findViewById(R.id.iv_topbar_right_detail);
        tv_topbar_title.setText("员工排行");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);
        iv_topbar_right_detail.setVisibility(View.VISIBLE);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        actId = getIntent().getStringExtra("actId");
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

    void setRv(ArrayList<Staff_cj> pinDans) {
        if (adapter == null) {
            pinDan_pts.addAll(pinDans);
            adapter = new StaffRankingAdapter(context, pinDan_pts);
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
        findViewById(R.id.iv_topbar_right_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, TichengDetailActivity.class));
            }
        });
    }

    void getData(){
        if (actId == null) {
            HttpMethods.start(HttpMethods.getInstance().demoService.getStaffRanking_cj(token, page, 20, 0), new Subscriber<Response<ArrayList<Staff_cj>>>() {
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
                public void onNext(Response<ArrayList<Staff_cj>> arrayListResponse) {
                    if (arrayListResponse.data != null) {
                        setRv(arrayListResponse.data);
                        canGet = true;
                        page++;
                    }
                }
            });
        } else {
            HttpMethods.start(HttpMethods.getInstance().demoService.getStaffRanking_cj(token, page, 20, 0, actId), new Subscriber<Response<ArrayList<Staff_cj>>>() {
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
                public void onNext(Response<ArrayList<Staff_cj>> arrayListResponse) {
                    if (arrayListResponse.data != null) {
                        setRv(arrayListResponse.data);
                        canGet = true;
                        page++;
                    }
                }
            });
        }
    }

}
