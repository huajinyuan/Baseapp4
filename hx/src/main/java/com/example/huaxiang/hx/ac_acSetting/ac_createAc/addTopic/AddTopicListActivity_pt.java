package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addAward.AddAwardActivity_cj;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.adapter.AddTopicListAdapter;
import com.example.huaxiang.hx.ac_bb.m.HxTopic;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

import static com.example.huaxiang.R.attr.position;

@RequiresPresenter(AddTopicListPresenter_pt.class)
public class AddTopicListActivity_pt extends BaseActivity<AddTopicListPresenter_pt> {
    public static AddTopicListActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    AddTopicListAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<HxTopic> topics = new ArrayList<>();
    String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_award_list_hx;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("中奖设置");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        id = getIntent().getStringExtra("id");
        getData();
    }

    void setRv() {
        adapter = new AddTopicListAdapter(context, topics);
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
        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddTopicActivity_cj.class).putExtra("id", id));
            }
        });
    }

    void getData() {
        HttpMethods.start(HttpMethods.getInstance().demoService.getTopicList(token, id), new Subscriber<Response<ArrayList<HxTopic>>>() {
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
            public void onNext(Response<ArrayList<HxTopic>> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    topics = arrayListResponse.data;
                    setRv();
                }
            }
        });

    }

    public void deleteAward(String awardId) {
        HttpMethods.start(HttpMethods.getInstance().demoService.deleteAward(token, awardId), new Subscriber<Response>() {
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
                    Toast.makeText(context, "删除奖品成功", Toast.LENGTH_SHORT).show();
                    getData();
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
