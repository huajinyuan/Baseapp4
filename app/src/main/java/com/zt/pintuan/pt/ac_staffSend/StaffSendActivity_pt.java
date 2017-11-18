package com.zt.pintuan.pt.ac_staffSend;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.model.Response;
import com.zt.pintuan.module.base.BaseActivity;
import com.zt.pintuan.network.retrofit.HttpMethods;
import com.zt.pintuan.pt.ac_staffSend.adapter.StaffSendAdapter_pt;
import com.zt.pintuan.pt.ac_staffSend.addStaffSend.SelectStaffActivity_pt;
import com.zt.pintuan.pt.ac_staffSend.m.StaffSend_pt;
import com.zt.pintuan.utils.ACache;
import com.zt.pintuan.utils.ACacheKey;

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
    StaffSendAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<StaffSend_pt> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;
    SwipeRefreshLayout swip_refresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_send_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("员工发送");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);
        swip_refresh = findView(R.id.swip_refresh);
        swip_refresh.setColorSchemeResources(R.color.colorAppRed, R.color.colorMyGreen, R.color.colorMyBlue);
        swip_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
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

    void setRv(ArrayList<StaffSend_pt> pinDans) {
        if (adapter == null) {
            pinDan_pts.clear();
            pinDan_pts.addAll(pinDans);
            adapter = new StaffSendAdapter_pt(context, pinDan_pts);
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
        findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SelectStaffActivity_pt.class));
            }
        });
    }
    void getData(){
        HttpMethods.getInstance().getStaffSends(token, page, 10).subscribe(new Subscriber<Response<ArrayList<StaffSend_pt>>>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.e("=============", "onStart");
                canGet = false;
                swip_refresh.setRefreshing(true);
            }

            @Override
            public void onCompleted() {
                Log.e("=============", "onCompleted");
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("=======onError", e.toString() + "");
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onNext(Response<ArrayList<StaffSend_pt>> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    setRv(arrayListResponse.data);
                    canGet = true;
                    page++;
                }
            }
        });
    }

    void refresh(){
        adapter = null;
        page = 1;
        getData();
    }


}
