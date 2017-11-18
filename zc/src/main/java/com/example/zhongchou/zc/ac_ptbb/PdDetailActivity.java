package com.example.zhongchou.zc.ac_ptbb;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhongchou.R;
import com.example.zhongchou.model.Response;
import com.example.zhongchou.module.base.BaseActivity;
import com.example.zhongchou.network.retrofit.HttpMethods;
import com.example.zhongchou.zc.ac_ptbb.adapter.PdDetailMemberAdapter;
import com.example.zhongchou.zc.ac_ptbb.m.Member_pt;
import com.example.zhongchou.zc.ac_ptbb.m.PdDetail;
import com.example.zhongchou.utils.ACache;
import com.example.zhongchou.utils.ACacheKey;
import com.example.zhongchou.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(PdDetailPresenter.class)
public class PdDetailActivity extends BaseActivity<PdDetailPresenter> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    PdDetailMemberAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Member_pt> pinDan_pts = new ArrayList<>();

    String orderNumber;
    PdDetail pdDetail;
    SwipeRefreshLayout swip_refresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pd_detail;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("拼单明细");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("拼团");
        iv_topbar_right.setVisibility(View.GONE);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

        swip_refresh = findView(R.id.swip_refresh);
        swip_refresh.setColorSchemeResources(R.color.colorAppRed, R.color.colorMyGreen, R.color.colorMyBlue);
        swip_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        orderNumber = getIntent().getStringExtra("orderNumber");
        if (orderNumber != null) {
            getData();
        }
    }

    void setRv(ArrayList<Member_pt> pinDans) {
        pinDan_pts.clear();
        pinDan_pts.addAll(pinDans);
        adapter = new PdDetailMemberAdapter(context, pinDan_pts);
        layoutManager = new LinearLayoutManager(context);
        rv_staffSend.setLayoutManager(layoutManager);
        rv_staffSend.setAdapter(adapter);
    }
    void setData(){
        tv_topbar_right.setVisibility(pdDetail.status == 2 ? View.GONE : View.VISIBLE);
        ((TextView) findView(R.id.tv_name)).setText(pdDetail.actName);
        ((TextView) findView(R.id.tv_price)).setText("￥" + pdDetail.price);
        ((TextView) findView(R.id.tv_number)).setText(pdDetail.orderNumber);
        ((TextView) findView(R.id.tv_status)).setText(pdDetail.status == 1 ? "待成团" : pdDetail.status == 2 ? "已成团" : "失败");
        ((TextView) findView(R.id.tv_num)).setText(pdDetail.groupNum + "/" + pdDetail.count);
        ((TextView) findView(R.id.tv_date)).setText(pdDetail.updateDate);
        ((TextView) findView(R.id.tv_user)).setText(pdDetail.user.name);
        ((TextView) findView(R.id.tv_redeemCode)).setText(pdDetail.orderNumber);

        if (pdDetail.members != null) {
            setRv(pdDetail.members);
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
                if (pdDetail != null) {
                    pintuan(pdDetail.id);
                }
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getPdDetail(token, orderNumber), new Subscriber<Response<PdDetail>>() {
            @Override
            public void onStart() {
                super.onStart();
                swip_refresh.setRefreshing(true);
            }

            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onNext(Response<PdDetail> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    pdDetail = arrayListResponse.data;
                    setData();
                }
            }
        });
    }

    void pintuan(String id){
        HttpMethods.start(HttpMethods.getInstance().demoService.pintuan(token, id), new Subscriber<Response>() {
            @Override
            public void onStart() {
                super.onStart();
                swip_refresh.setRefreshing(true);
            }

            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
                swip_refresh.setRefreshing(false);
                Toast.makeText(context, "系统拼团失败，请重新拼团" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Response arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    Toast.makeText(context, "系统拼团成功", Toast.LENGTH_SHORT).show();
                    getData();
                } else {
                    Toast.makeText(context, "系统拼团失败，请重新拼团" + arrayListResponse.msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
