package com.example.choujiang.cj.ac_acSetting.ac_createAc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_acSetting.AcInfoActivity_cj;
import com.example.choujiang.cj.ac_acSetting.AcListPresenter_cj;
import com.example.choujiang.cj.ac_acSetting.adapter.WinHistoryAdapter_cj;
import com.example.choujiang.cj.ac_acSetting.lucky.LuckyMonkeyPanelView;
import com.example.choujiang.cj.ac_acSetting.m.ActivityDetail_cj;
import com.example.choujiang.cj.ac_cjbb.m.CjHistory;
import com.example.choujiang.cj.ac_staffSend.m.Activity_cj;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;

import java.util.ArrayList;
import java.util.Random;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(AcListPresenter_cj.class)
public class CreateAcActivity_cj extends BaseActivity<CreateAcPresenter_cj> {
    public static CreateAcActivity_cj instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;
    LuckyMonkeyPanelView lucky_panel;

    String  id;
    ActivityDetail_cj data = new ActivityDetail_cj();
    RecyclerView rv_winHistory;
    TextView tv_name;
    TextView tv_available;
    TextView tv_tip;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_ac_cj;
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
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        lucky_panel = findView(R.id.lucky_panel);
        rv_winHistory = findView(R.id.rv_winHistory);
        tv_name = findView(R.id.tv_name);
        tv_available = findView(R.id.tv_available);
        tv_tip = findView(R.id.tv_tip);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        id = getIntent().getStringExtra("id");
        setData();
        if (id != null) {
            getData();
        }
    }

    public void setData(){
        //活动信息
        if (data.name != null)
            tv_name.setText(data.name);
        if (data.remarks != null)
            tv_tip.setText(data.remarks);
        if (data.beginTime != null && data.endTime != null)
            tv_available.setText("时间：" + data.beginTime + "-" + data.endTime);

        //中奖记录
        if (data.awardDetails != null) {
            setRv_winHistory(data.awardDetails);
            if (data.awardDetails.size() > 1) {
                findViewById(R.id.view_win).setVisibility(View.GONE);
            }
        } else {
            data.awardDetails = new ArrayList<>();
        }
        //添加转盘商品
        if (data.awards != null) {
            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0; i < data.awards.size(); i++) {
                urls.add(data.awards.get(i).imgUrl);
            }
            lucky_panel.setImage(urls);
        } else {
            data.awards = new ArrayList<>();
        }
    }

    void setRv_winHistory(ArrayList<CjHistory> cjHistories){
        WinHistoryAdapter_cj adapter_cj = new WinHistoryAdapter_cj(context, cjHistories);
        adapter_cj.setClickListener(new WinHistoryAdapter_cj.ClickListener() {
            @Override
            public void click() {
                if (data.id == null) {
                    Toast.makeText(context, "请先添加活动！", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, AddWinListActivity_pt.class).putExtra("id", data.id));
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_winHistory.setLayoutManager(layoutManager);
        rv_winHistory.setAdapter(adapter_cj);
    }
    @Override
    protected void setListener() {
        findView(R.id.iv_topbar_back).setOnClickListener(view -> finish());

        findViewById(R.id.bt_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lucky_panel.isGameRunning()) {
                    lucky_panel.startGame();
                } else {
                    int stayIndex = new Random().nextInt(8);
                    Log.e("LuckyMonkeyPanelView", "====stay===" + stayIndex);
                    lucky_panel.tryToStop(stayIndex);
                }
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AcInfoActivity_cj.instance != null) {
                    AcInfoActivity_cj.instance.getData();
                }
                finish();
            }
        });
        findViewById(R.id.img_tip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddAcActivity_cj.class));
            }
        });
        findViewById(R.id.view_win).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.id == null) {
                    Toast.makeText(context, "请先添加活动！", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, AddWinListActivity_pt.class).putExtra("id", data.id));
                }
            }
        });

        lucky_panel.setClickListener(new LuckyMonkeyPanelView.ClickListener() {
            @Override
            public void click(int position) {
                if (data.id == null) {
                    Toast.makeText(context, "请先添加活动！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, AddAwardActivity_cj.class);
                    intent.putExtra("position", position);
                    if (position < data.awards.size()) {
                        intent.putExtra("award", data.awards.get(position));
                    }
                    startActivity(intent);
                }
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
                if (arrayListResponse.data != null) {
                    data = arrayListResponse.data;
                    setData();
                }
            }
        });
    }

    void addAc(){
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAc(token, data.name, data.imgUrl, data.beginTime, data.endTime, data.count + "", data.shareCount + "", data.exchangeCount + "", data.remarks), new Subscriber<Response<Activity_cj>>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<Activity_cj> arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    data.id = arrayListResponse.data.id;
                    Toast.makeText(context, "添加活动成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void editAc(){
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAc(token, data.name, data.imgUrl, data.beginTime, data.endTime, data.count + "", data.shareCount + "", data.exchangeCount + "", data.remarks, data.id), new Subscriber<Response>() {
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
