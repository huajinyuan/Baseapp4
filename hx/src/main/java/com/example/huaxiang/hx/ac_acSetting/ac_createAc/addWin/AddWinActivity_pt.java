package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addWin;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.CreateAcActivity_cj;
import com.example.huaxiang.hx.ac_acSetting.m.Award;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

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
        return R.layout.activity_create_add_win_hx;
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
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

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
                if (!isMobileNO(mobile)) {
                    Toast.makeText(context, "手机格式不正确", Toast.LENGTH_SHORT).show();
                } else if (award == null) {
                    Toast.makeText(context, "请选择奖品", Toast.LENGTH_SHORT).show();
                } else {
                    addAward(mobile);
                }
            }
        });
    }

    void addAward(String mobile){
        HttpMethods.start(HttpMethods.getInstance().demoService.addWinHistory(token, id, mobile, award.id), new Subscriber<Response>() {
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
                    Toast.makeText(context, "添加记录成功", Toast.LENGTH_SHORT).show();
                    AddWinListActivity_pt.instance.getData();
                    CreateAcActivity_cj.instance.getData();
                    finish();
                }
            }
        });

    }

    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
