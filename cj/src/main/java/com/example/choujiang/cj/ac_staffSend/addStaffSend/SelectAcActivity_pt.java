package com.example.choujiang.cj.ac_staffSend.addStaffSend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_staffSend.addStaffSend.adapter.SelectAcAdapter_pt;
import com.example.choujiang.cj.ac_staffSend.m.Activity_cj;
import com.example.choujiang.cj.ac_staffSend.staffDetail.StaffDetailActivity_pt;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;
import com.example.choujiang.utils.ToastUtil;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(SelectAcPresenter_pt.class)
public class SelectAcActivity_pt extends BaseActivity<SelectAcPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    SelectAcAdapter_pt adapter;
    String userId;

    LinearLayoutManager layoutManager;
    ArrayList<Activity_cj> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_ac_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("选择活动");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        userId = getIntent().getStringExtra("userId");
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
            adapter = new SelectAcAdapter_pt(context, pinDan_pts);
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
        findViewById(R.id.bt_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    String acIds = "";
                    for (Activity_cj activity_cj : adapter.data) {
                        if (activity_cj.checked) {
                            acIds += activity_cj.id + ",";
                        }
                    }
                    if (!acIds.equals("")) {
                        saveData(acIds, userId);
                    }
                }
            }
        });
    }

    void getData(){
        if (userId != null) {
            HttpMethods.start(HttpMethods.getInstance().demoService.getAc_cj(token, page, 10, 0), new Subscriber<Response<ArrayList<Activity_cj>>>() {
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

    void saveData(String activityIds, String userId) {
        HttpMethods.start(HttpMethods.getInstance().demoService.saveStaffSend(token, activityIds, userId), new Subscriber<Response>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    ToastUtil.showToast("提交成功");
                    finish();
                    if (SelectStaffActivity_pt.instance != null) {
                        SelectStaffActivity_pt.instance.finish();
                    }
                    if (StaffDetailActivity_pt.instance==null){
                        startActivity(new Intent(context, StaffDetailActivity_pt.class).putExtra("userId", Integer.parseInt(userId)));
                    }
                } else {
                    ToastUtil.showToast(arrayListResponse.msg);
                }
            }
        });
    }

}
