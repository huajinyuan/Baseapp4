package com.example.huaxiang.hx.ac_bb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.adapter.ReplaceListAdapter;
import com.example.huaxiang.hx.ac_bb.m.CjDetail;
import com.example.huaxiang.hx.ac_memberget.m.CjHistory;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;
import com.example.huaxiang.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(CjDetailPresenter_hx.class)
public class CjDetailActivity_hx extends BaseActivity<CjDetailPresenter_hx> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_replace;
    ReplaceListAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<CjHistory> pinDan_pts = new ArrayList<>();

    String number;
    CjDetail cjDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cj_detail;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("抽奖记录");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        rv_replace = (RecyclerView) findViewById(R.id.rv_replace);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        number = getIntent().getStringExtra("number");
        if (number != null) {
            getData();
        }
    }

    void setRv(ArrayList<CjHistory> pinDans) {
        pinDan_pts = pinDans;
        adapter = new ReplaceListAdapter(context, pinDan_pts);
        layoutManager = new LinearLayoutManager(context);
        rv_replace.setLayoutManager(layoutManager);
        rv_replace.setAdapter(adapter);
    }
    void setData(){
        ((TextView) findView(R.id.tv_name)).setText(cjDetail.memberName);
        ((TextView) findView(R.id.tv_carNumber)).setText(cjDetail.licensePlate);
        ((TextView) findView(R.id.tv_phone)).setText(cjDetail.phone);
        ((TextView) findView(R.id.tv_cjNumber)).setText(cjDetail.number);
        ((TextView) findView(R.id.tv_acName)).setText(cjDetail.actName);
        ((TextView) findView(R.id.tv_payMoney)).setText(cjDetail.money + "");
        ((TextView) findView(R.id.tv_cjTime)).setText(cjDetail.createDate);
        ((TextView) findView(R.id.tv_awardName)).setText(cjDetail.awardName);
        ((TextView) findView(R.id.tv_replaceTime)).setText(cjDetail.replaced + "/" + cjDetail.replaceTime);

        if (cjDetail.hxAwardDetails != null) {
            setRv(cjDetail.hxAwardDetails);
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
        findViewById(R.id.ll_topic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cjDetail != null) {
                    startActivity(new Intent(context, CjDetailTopicActivity_pt.class).putExtra("actId", cjDetail.id));
                }
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getCjDetail(token, number), new Subscriber<Response<CjDetail>>() {
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
            public void onNext(Response<CjDetail> arrayListResponse) {
                if (arrayListResponse != null) {
                    cjDetail = arrayListResponse.data;
                    setData();
                }
            }
        });

    }


}
