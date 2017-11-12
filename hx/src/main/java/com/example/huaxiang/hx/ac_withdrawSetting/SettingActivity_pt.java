package com.example.huaxiang.hx.ac_withdrawSetting;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_withdrawSetting.m.WithdrawSetting;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(SettingPresenter_pt.class)
public class SettingActivity_pt extends BaseActivity<SettingPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    CheckBox cb_persent;
    CheckBox cb_num;
    EditText et1, et2;

    int type = 1;
    int value1, value2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_hx;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("提成设置");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("提现");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        cb_persent = (CheckBox) findViewById(R.id.cb_persent);
        cb_num = (CheckBox) findViewById(R.id.cb_num);
        et1 = findView(R.id.et1);
        et2 = findView(R.id.et2);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);

        getData();
    }

    void setData(WithdrawSetting setting) {
        type = setting.type;
        checkCb(type);
        et1.setText(setting.value1 + "");
        et2.setText(setting.value2 + "");
    }


    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.rl_persent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCb(1);
            }
        });
        findViewById(R.id.rl_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCb(2);
            }
        });
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AccountListActivity_cj.class));
            }
        });

        findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et1.getText().toString().trim().equals("") || et2.getText().toString().trim().equals("")) {
                    Toast.makeText(context, "不可为空", Toast.LENGTH_SHORT).show();
                } else {
                    value1 = Integer.parseInt(et1.getText().toString().trim());
                    value2 = Integer.parseInt(et2.getText().toString().trim());

                    saveSetting(type, type == 1 ? value1 : value2);
                }
            }
        });
    }

    private void checkCb(int position){
        cb_persent.setChecked(false);
        cb_num.setChecked(false);
        if (position == 1) {
            cb_persent.setChecked(true);
        } else {
            cb_num.setChecked(true);
        }
        type = position;
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getSetting(token, "4"), new Subscriber<Response<WithdrawSetting>>() {
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
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<WithdrawSetting> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    setData(arrayListResponse.data);
                }
            }
        });

    }

    void saveSetting(int type, int value) {
        HttpMethods.start(HttpMethods.getInstance().demoService.saveSetting(token, "4", type, value), new Subscriber<Response>() {
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
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, arrayListResponse.code, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
