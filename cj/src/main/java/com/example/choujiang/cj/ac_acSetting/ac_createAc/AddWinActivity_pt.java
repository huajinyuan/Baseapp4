package com.example.choujiang.cj.ac_acSetting.ac_createAc;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_acSetting.m.Award;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

import static com.example.choujiang.R.attr.position;

@RequiresPresenter(AddWinPresenter_pt.class)
public class AddWinActivity_pt extends BaseActivity<AddWinPresenter_pt> {
    public static AddWinActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    EditText et_mobile;
    public EditText et_award;
    public Award award;
    String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_add_win_cj;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("新增记录");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        et_mobile = findView(R.id.et_mobile);
        et_award = findView(R.id.et_award);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        id = getIntent().getStringExtra("id");
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
                startActivity(new Intent(context, AwardListActivity_cj.class).putExtra("id", id));
            }
        });
        findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = et_mobile.getText().toString().trim();
                if (mobile.length()!=11) {
                    Toast.makeText(context, "请填写11位手机号", Toast.LENGTH_SHORT).show();
                } else if (award == null) {
                    Toast.makeText(context, "请选择奖品", Toast.LENGTH_SHORT).show();
                } else {
                    saveData(mobile);
                }
            }
        });
    }

    void saveData(String mobile) {
        HttpMethods.start(HttpMethods.getInstance().demoService.saveWinHistory(token, id, mobile, award.id), new Subscriber<Response>() {
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
                    Toast.makeText(context, "添加记录成功", Toast.LENGTH_SHORT).show();
                    AddWinListActivity_pt.instance.getData();
                    finish();
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
