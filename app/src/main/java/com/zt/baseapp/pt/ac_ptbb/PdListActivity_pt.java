package com.zt.baseapp.pt.ac_ptbb;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.model.Response;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.network.retrofit.HttpMethods;
import com.zt.baseapp.pt.ac_ptbb.adapter.PdListAdapter_pt;
import com.zt.baseapp.pt.ac_ptbb.m.PinDan_pt;
import com.zt.baseapp.utils.ACache;
import com.zt.baseapp.utils.ACacheKey;

import java.util.ArrayList;

import rx.Subscriber;


public class PdListActivity_pt extends BaseActivity<PdListPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    PdListAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<PinDan_pt> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pt_list;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("拼单记录");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

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

    void setRv(ArrayList<PinDan_pt> pinDans) {
        if (adapter == null) {
            pinDan_pts.addAll(pinDans);
            adapter = new PdListAdapter_pt(context, pinDan_pts);
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
        findViewById(R.id.iv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getPdList_pt(token, page, 10, 0), new Subscriber<Response<ArrayList<PinDan_pt>>>() {
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
            public void onNext(Response<ArrayList<PinDan_pt>> arrayListResponse) {
                setRv(arrayListResponse.data);
                canGet = true;
                page++;
            }
        });

    }

}