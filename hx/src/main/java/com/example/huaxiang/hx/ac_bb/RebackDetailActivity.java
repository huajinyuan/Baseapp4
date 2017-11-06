package com.example.huaxiang.hx.ac_bb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.adapter.RecordListAdapter;
import com.example.huaxiang.hx.ac_bb.m.Record;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import java.util.ArrayList;


public class RebackDetailActivity extends BaseActivity<RebackDetailPresenter> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;
    ImageView iv_topbar_right_detail;

    RecyclerView rv_staffSend;
    ArrayList<Record> records = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reback_detail_hx;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        iv_topbar_right_detail = (ImageView) findViewById(R.id.iv_topbar_right_detail);
        tv_topbar_title.setText("回访详情");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("操作");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);

    }

    void setRv(ArrayList<Record> recordsData) {
        RecordListAdapter adapter = new RecordListAdapter(context, recordsData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_staffSend.setLayoutManager(layoutManager);
        rv_staffSend.setAdapter(adapter);
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
//        HttpMethods.start(HttpMethods.getInstance().demoService.getRebackList(token, page, 10, type), new Subscriber<Response<ArrayList<Reback_hx>>>() {
//            @Override
//            public void onStart() {
//                super.onStart();
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.e("aaa", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("aaa", "onError" + e.getMessage());
//            }
//
//            @Override
//            public void onNext(Response<ArrayList<Reback_hx>> arrayListResponse) {
//                if (arrayListResponse.data != null) {
//                    setRv(arrayListResponse.data);
//                }
//            }
//        });
    }

}
