package com.example.huaxiang.hx.ac_acSetting;

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
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.CreateAcActivity_cj;
import com.example.huaxiang.hx.ac_acSetting.adapter.AcDetailTopicAdapter;
import com.example.huaxiang.hx.ac_acSetting.adapter.WinHistoryAdapter_cj;
import com.example.huaxiang.hx.ac_acSetting.m.ActivityDetail_cj;
import com.example.huaxiang.hx.ac_bb.m.HxTopic;
import com.example.huaxiang.hx.ac_memberget.m.CjHistory;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;
import com.example.huaxiang.utils.UiUtil;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(AcListPresenter_pt.class)
public class AcInfoActivity_pt extends BaseActivity<AcInfoPresenter_pt> {
    public static AcInfoActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    String id;

    RecyclerView rv_winHistory;
    RecyclerView rv_topic;
    ImageView iv_ac;
    TextView tv_price;
    TextView tv_name;
    TextView tv_num;
    TextView tv_stop;
    TextView tv_abandon;

    ActivityDetail_cj data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ac_info_main;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("活动详情");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("编辑");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        rv_winHistory = findView(R.id.rv_winHistory);
        rv_topic = findView(R.id.rv_topic);
        iv_ac = findView(R.id.iv_ac);
        tv_price = findView(R.id.tv_price);
        tv_name = findView(R.id.tv_name);
        tv_num = findView(R.id.tv_num);
        tv_stop = findView(R.id.tv_stop);
        tv_abandon = findView(R.id.tv_abandon);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        id = getIntent().getStringExtra("id");
        getData();
    }

    void setData(){
        UiUtil.setImage(iv_ac, data.imgUrl);
        tv_price.setText(data.money + "元博");
        tv_name.setText(data.name);
        tv_num.setText(data.num + "人参与");

        //检查活动状态
        if (data.status == 2) {
            tv_stop.setText("启用");
            tv_abandon.setText("作废");
        } else if (data.status == 3) {
            tv_stop.setText("启用");
            tv_abandon.setText("已作废");
        } else if (data.status == 1) {
            tv_stop.setText("停用");
            tv_abandon.setText("作废");
        }
        tv_stop.setBackgroundResource(data.status == 3 ? R.drawable.shape_radius5_grey : R.drawable.shape_radius5_blue);

        //添加中奖列表
        if (data.details != null) {
            setRv_winHistory(data.details);
        }

        //添加问卷
        if (data.topics != null) {
            setRv_topic(data.topics);
        }
    }

    void setRv_winHistory(ArrayList<CjHistory> cjHistories){
        WinHistoryAdapter_cj adapter_cj = new WinHistoryAdapter_cj(context, cjHistories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_winHistory.setLayoutManager(layoutManager);
        rv_winHistory.setAdapter(adapter_cj);
    }

    void setRv_topic(ArrayList<HxTopic> hxTopics){
        AcDetailTopicAdapter adapter = new AcDetailTopicAdapter(context, hxTopics);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_topic.setLayoutManager(layoutManager);
        rv_topic.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        findView(R.id.iv_topbar_back).setOnClickListener(view -> finish());
        findView(R.id.tv_topbar_right).setOnClickListener(view -> startActivity(new Intent(context, CreateAcActivity_cj.class).putExtra("id", data.id)));
        findView(R.id.tv_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.status == 2) {
                    changeAcStatus(1);
                } else if (data.status == 1) {
                    changeAcStatus(2);
                }

            }
        });
        findView(R.id.tv_abandon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.status != 3) {
                    changeAcStatus(3);
                }
            }
        });
    }

    public void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getAcDetail_cj(token, id), new Subscriber<Response<ActivityDetail_cj>>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<ActivityDetail_cj> arrayListResponse) {
                data = arrayListResponse.data;
                if (data != null) {
                    setData();
                }
            }
        });
    }

    void changeAcStatus(int status){
        HttpMethods.start(HttpMethods.getInstance().demoService.changeAcStatus(token, data.id, status), new Subscriber<Response>() {
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
                    if (status == 2) {
                        Toast.makeText(context, "已停用", Toast.LENGTH_SHORT).show();
                    } else if (status == 3) {
                        Toast.makeText(context, "已作废", Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        Toast.makeText(context, "已启用", Toast.LENGTH_SHORT).show();
                    }
                }
                getData();
            }
        });
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
