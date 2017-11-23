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
import com.example.huaxiang.hx.ac_acSetting.AcInfoActivity_pt;
import com.example.huaxiang.hx.ac_acSetting.AcListActivity_pt;
import com.example.huaxiang.hx.ac_acSetting.AcListPresenter_pt;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addAward.AddAwardListActivity_pt;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addReplace.AddReplaceActivity_pt;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.AddTopicListActivity_pt;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addWin.AddWinListActivity_pt;
import com.example.huaxiang.hx.ac_acSetting.adapter.AcDetailTopicAdapter;
import com.example.huaxiang.hx.ac_acSetting.adapter.WinHistoryAdapter_cj;
import com.example.huaxiang.hx.ac_acSetting.m.ActivityDetail_cj;
import com.example.huaxiang.hx.ac_acSetting.m.Award;
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
    TextView tv_replace_num;
    TextView tv_replace_awardName;

    ActivityDetail_cj data = new ActivityDetail_cj();

    public String replaceNum;
    public Award replaceaward;

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
        tv_replace_num = findView(R.id.tv_replace_num);
        tv_replace_awardName = findView(R.id.tv_replace_awardName);
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

    public void setData(){
        if (data.imgUrl != null)
            UiUtil.setImage(iv_ac, data.imgUrl);
        if (data.name != null)
            tv_name.setText(data.name);
        tv_price.setText(data.money + "块钱博");
        tv_num.setText(data.num + "人参与");

        //添加中奖列表
        if (data.details != null) {
            setRv_winHistory(data.details);
        } else {
            data.details = new ArrayList<>();
        }

//        //改 显示假数据 中奖列表
//        ArrayList<CjHistory> fakeCjHistorys = new ArrayList<>();
//        CjHistory fakeCjHistory = new CjHistory();
//        fakeCjHistory.mobile = "13875758686";
//        fakeCjHistory.awardName = "铠甲镀晶";
//        fakeCjHistorys.add(fakeCjHistory);
//        setRv_winHistory(fakeCjHistorys);

        //添加代抽
        if (replaceNum != null) {
            tv_replace_num.setText(replaceNum);
        }
        if (replaceaward != null) {
            tv_replace_awardName.setText(replaceaward.name);
        }

        //添加问卷
        if (data.topics != null) {
            setRv_topic(data.topics);
            if (data.topics.size() > 0) {
                findView(R.id.tv_tip_addTopic).setVisibility(View.GONE);
            }
        } else {
            data.topics = new ArrayList<>();
        }
    }

    void setRv_winHistory(ArrayList<CjHistory> cjHistories){
        WinHistoryAdapter_cj adapter_cj = new WinHistoryAdapter_cj(context, cjHistories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_winHistory.setLayoutManager(layoutManager);
        rv_winHistory.setAdapter(adapter_cj);

        adapter_cj.setClickListener(new WinHistoryAdapter_cj.ClickListener() {
            @Override
            public void click() {
                if (data.id == null) {
                    Toast.makeText(context, "请先添加活动", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, AddWinListActivity_pt.class).putExtra("id", id));
                }
            }
        });
    }

    void setRv_topic(ArrayList<HxTopic> hxTopics){
        AcDetailTopicAdapter adapter = new AcDetailTopicAdapter(context, hxTopics);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_topic.setLayoutManager(layoutManager);
        rv_topic.setAdapter(adapter);

        adapter.setClickListener(new AcDetailTopicAdapter.ClickListener() {
            @Override
            public void click() {
                if (data.id == null) {
                    Toast.makeText(context, "请先添加活动", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, AddTopicListActivity_pt.class).putExtra("id", id));
                }
            }
        });
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
                    startActivity(new Intent(context, AddAwardListActivity_pt.class).putExtra("id", data.id));
                }
            }
        });
        findView(R.id.rl_add_win).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.id == null) {
                    Toast.makeText(context, "请先添加活动", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, AddWinListActivity_pt.class).putExtra("id", data.id));
                }
            }
        });
        findView(R.id.ll_replace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.id == null) {
                    Toast.makeText(context, "请先添加活动", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, AddReplaceActivity_pt.class).putExtra("id", data.id));
                }
            }
        });
        findView(R.id.tv_tip_addTopic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.id == null) {
                    Toast.makeText(context, "请先添加活动", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, AddTopicListActivity_pt.class).putExtra("id", data.id));
                }
            }
        });

        findView(R.id.rl_add_ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddAcActivity_cj.class));
            }
        });

        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AcInfoActivity_pt.instance != null) {
                    AcInfoActivity_pt.instance.getData();
                }
                if (AcListActivity_pt.instance != null) {
                    AcListActivity_pt.instance.refresh();
                }
                finish();
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
                if (arrayListResponse.data != null) {
                    data = arrayListResponse.data;
                    setData();
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
