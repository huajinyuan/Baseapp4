package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import nucleus.factory.RequiresPresenter;


@RequiresPresenter(AddTopicPresenter_cj.class)
public class AddTopicActivity_cj extends BaseActivity<AddTopicPresenter_cj> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    String id;
    Switch switch_duoxuan;
    EditText et_question;
    RecyclerView rv_addTopic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_topic_hx;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("新增问卷");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        switch_duoxuan = findView(R.id.switch_duoxuan);
        et_question = findView(R.id.et_question);
        rv_addTopic = findView(R.id.rv_addTopic);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        id = getIntent().getStringExtra("id");
    }

    void setData(){

    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    void addAward(){
//        HttpMethods.start(HttpMethods.getInstance().demoService.saveAward(token, id, name, price, num, awardOdds, replaceOdds, imgUrl), new Subscriber<Response<Award>>() {
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
//            public void onNext(Response<Award> arrayListResponse) {
//                if (arrayListResponse.code == 0) {
//                    Toast.makeText(context, "添加奖品成功", Toast.LENGTH_SHORT).show();
//                    AddAwardListActivity_pt.instance.getData();
//                    finish();
//                }
//            }
//        });

    }
    void editAward(){
//        HttpMethods.start(HttpMethods.getInstance().demoService.saveAward(token, id, name, price, num, awardOdds, replaceOdds, imgUrl, award.id), new Subscriber<Response>() {
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
//            public void onNext(Response arrayListResponse) {
//                if (arrayListResponse.code == 0) {
//                    Toast.makeText(context, "修改活动成功", Toast.LENGTH_SHORT).show();
//                    AddAwardListActivity_pt.instance.getData();
//                    finish();
//                }
//            }
//        });
    }

}
