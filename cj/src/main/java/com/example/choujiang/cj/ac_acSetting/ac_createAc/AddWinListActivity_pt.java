package com.example.choujiang.cj.ac_acSetting.ac_createAc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_acSetting.ac_createAc.adapter.AddWinListAdapter_pt;
import com.example.choujiang.cj.ac_cjbb.m.CjHistory;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(AddWinListPresenter_pt.class)
public class AddWinListActivity_pt extends BaseActivity<AddWinListPresenter_pt> {
    public static AddWinListActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    AddWinListAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<CjHistory> cjHistories = new ArrayList<>();
    String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_win_list_cj;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("中奖记录");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("保存");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        id = getIntent().getStringExtra("id");
    }

    void setRv() {
        adapter = new AddWinListAdapter_pt(context, cjHistories);
        layoutManager = new LinearLayoutManager(context);
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
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cjHistories.size() != 0) {
                    CreateAcActivity_cj.instance.data.awardDetails.addAll(cjHistories);
                    for(int i=0;i<cjHistories.size();i++) {
                        saveData(cjHistories.get(i), i);
                    }
                }
            }
        });
        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddWinActivity_pt.class).putExtra("id", id));
            }
        });
    }

    void saveData(CjHistory cjHistory, int position) {
        HttpMethods.start(HttpMethods.getInstance().demoService.saveWinHistory(token, id, cjHistory.mobile, cjHistory.awardId), new Subscriber<Response>() {
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
                Log.e("aaa_savecjHistory", "onError position" + position + e.getMessage());
            }

            @Override
            public void onNext(Response arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    cjHistories.get(position).uploaded = true;

                    boolean canFinish = true;
                    for (CjHistory ch : cjHistories) {
                        if (!ch.uploaded) {
                            canFinish = false;
                        }
                    }
                    if (canFinish) {
                        CreateAcActivity_cj.instance.setData();
                        finish();
                    }

                    Log.e("aaa_savecjHistory", "succeed position" + position);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
