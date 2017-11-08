package com.example.huaxiang.hx.ac_acSetting.ac_createAc;

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
import com.example.huaxiang.hx.ac_acSetting.AcListPresenter_pt;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addAward.AddAwardListActivity_pt;
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
public class CreateAcActivity_cj extends BaseActivity<CreateAcPresenter_cj> {
    public static CreateAcActivity_cj instance;
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

    ActivityDetail_cj data = new ActivityDetail_cj();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_ac_;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("新增活动");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        rv_winHistory = findView(R.id.rv_winHistory);
        rv_topic = findView(R.id.rv_topic);
        iv_ac = findView(R.id.iv_ac);
        tv_price = findView(R.id.tv_price);
        tv_name = findView(R.id.tv_name);
        tv_num = findView(R.id.tv_num);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        setData();
        id = getIntent().getStringExtra("id");
        if (id != null) {
            getData();
        }
    }

    void setData(){
        if (data.imgUrl != null)
            UiUtil.setImage(iv_ac, data.imgUrl);
        if (data.name != null)
            tv_name.setText(data.name);
        tv_price.setText(data.money + "元博");
        tv_num.setText(data.num + "人参与");

        //添加中奖列表
        if (data.details != null) {
            setRv_winHistory(data.details);
        } else {
            data.details = new ArrayList<>();
        }

        //添加问卷
        if (data.topics != null) {
            setRv_topic(data.topics);
        } else {
            data.topics = new ArrayList<>();
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
        findView(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findView(R.id.iv_ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.id == null) {
                    Toast.makeText(context, "请先添加活动", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, AddAwardListActivity_pt.class).putExtra("id", id));
                }
            }
        });
        findView(R.id.rl_add_ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddAcActivity_cj.class));
            }
        });
    }

    void getData(){
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

    void addAc(){
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAc(token, data.name, data.imgUrl, data.videoUrl, data.beginTime, data.endTime, data.money + "", data.num + "", data.saleNum + "", data.replaceTime + "", data.carCheck), new Subscriber<Response<ActivityDetail_cj>>() {
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
                if (arrayListResponse.code == 0) {
                    data.id = arrayListResponse.data.id;
                    Toast.makeText(context, "添加活动成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void editAc(){
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAc(token, data.name, data.imgUrl, data.videoUrl, data.beginTime, data.endTime, data.money + "", data.num + "", data.saleNum + "", data.replaceTime + "", data.carCheck, data.id), new Subscriber<Response>() {
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
                    Toast.makeText(context, "修改活动成功", Toast.LENGTH_SHORT).show();
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
