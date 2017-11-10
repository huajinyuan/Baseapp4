package com.zt.baseapp.pt.ac_memberget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zt.baseapp.R;
import com.zt.baseapp.model.Response;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.network.retrofit.HttpMethods;
import com.zt.baseapp.pt.ac_ptbb.m.PinDan_pt;
import com.zt.baseapp.utils.ACache;
import com.zt.baseapp.utils.ACacheKey;

import rx.Subscriber;


public class MemberGetActivity extends BaseActivity<MemberGetPresenter> {
    public static MemberGetActivity instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    EditText et_code;
    TextView tv_status;
    TextView tv_personName;
    TextView tv_goodsName;
    TextView tv_goodsNum;

    public String detailId;
    PinDan_pt pinDan_pt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_get_pt;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("立即兑换");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("记录");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        et_code = findView(R.id.et_code);
        tv_status = findView(R.id.tv_status);
        tv_personName = findView(R.id.tv_personName);
        tv_goodsName = findView(R.id.tv_goodsName);
        tv_goodsNum = findView(R.id.tv_goodsNum);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
    }

    void setData(){
        et_code.setText(detailId);
        tv_status.setText(pinDan_pt.codeStatus.equals("0") ? "未兑换" : "已兑换");
        if(pinDan_pt.user!=null) {
            tv_personName.setText(pinDan_pt.user.name);
        }
        tv_goodsName.setText(pinDan_pt.goodName);
        tv_goodsNum.setText(pinDan_pt.goodNum + "");
    }

    @Override
    protected void setListener() {
        findView(R.id.img_topbar_back).setOnClickListener(view -> finish());

        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MemberGetListActivity.class));
            }
        });

        findViewById(R.id.iv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(context, QrCodeActivity.class));
            }
        });
        findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinDan_pt != null) {
                    exchange();
                }
            }
        });
    }

    public void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getPinDanDetail(token, detailId), new Subscriber<Response<PinDan_pt>>() {
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
            public void onNext(Response<PinDan_pt> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    pinDan_pt = arrayListResponse.data;
                    setData();

                }
            }
        });
    }

    void exchange(){
        HttpMethods.start(HttpMethods.getInstance().demoService.PinDanExchange(token, detailId, pinDan_pt.redeemCode), new Subscriber<Response>() {
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
                    Toast.makeText(context, "兑换成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context, arrayListResponse.msg, Toast.LENGTH_SHORT).show();
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
