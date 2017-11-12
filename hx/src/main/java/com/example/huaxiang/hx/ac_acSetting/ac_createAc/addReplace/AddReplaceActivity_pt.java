package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addReplace;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.CreateAcActivity_cj;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(AddReplacePresenter_pt.class)
public class AddReplaceActivity_pt extends BaseActivity<AddReplacePresenter_pt> {
    public static AddReplaceActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    EditText et_num;
    public EditText et_award;
    String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_add_replace_hx;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("代抽设置");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        et_num = findView(R.id.et_num);
        et_award = findView(R.id.et_award);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        id = getIntent().getStringExtra("id");

        setData();
    }

    void setData(){
        if (CreateAcActivity_cj.instance.replaceNum != null) {
            et_num.setText(CreateAcActivity_cj.instance.replaceNum);
        }
        if (CreateAcActivity_cj.instance.replaceaward != null) {
            et_award.setText(CreateAcActivity_cj.instance.replaceaward.name);
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
        findViewById(R.id.et_award).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddReplaceAwardActivity_cj.class).putExtra("id", id));
            }
        });
        findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = et_num.getText().toString().trim();
                if (num.isEmpty()) {
                    Toast.makeText(context, "请填写代抽次数", Toast.LENGTH_SHORT).show();
                } else if (CreateAcActivity_cj.instance.replaceaward == null) {
                    Toast.makeText(context, "请填写奖品", Toast.LENGTH_SHORT).show();
                } else {
                    CreateAcActivity_cj.instance.replaceNum = num;
                    CreateAcActivity_cj.instance.setData();
                    finish();
                }
            }
        });
    }

//    void addAward(String mobile){
//        HttpMethods.start(HttpMethods.getInstance().demoService.addWinHistory(token, id, mobile, award.id), new Subscriber<Response>() {
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
//                    Toast.makeText(context, "添加记录成功", Toast.LENGTH_SHORT).show();
//                    CreateAcActivity_cj.instance.getData();
//                    finish();
//                }
//            }
//        });
//
//    }


    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
