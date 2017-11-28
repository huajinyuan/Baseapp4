package com.example.huaxiang.hx.ac_memberget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_memberget.m.CjHistory;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    TextView tv_goodsPrice;
    TextView tv_goodsNum;

    public String detailId;
    CjHistory cjHistory;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_get_hx;
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
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        et_code = findView(R.id.et_code);
        tv_status = findView(R.id.tv_status);
        tv_personName = findView(R.id.tv_personName);
        tv_goodsName = findView(R.id.tv_goodsName);
        tv_goodsPrice = findView(R.id.tv_goodsPrice);
        tv_goodsNum = findView(R.id.tv_goodsNum);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
    }

    void setData(){
        et_code.setText(detailId);
        tv_status.setText(cjHistory.codeStatus.equals("0") ? "未兑换" : "已兑换");
        tv_personName.setText(cjHistory.member != null ? cjHistory.member.name : "--");
        tv_goodsName.setText(cjHistory.awardName);
        tv_goodsPrice.setText(cjHistory.price + "");
        tv_goodsNum.setText(cjHistory.awardNum + "");
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(view -> finish());
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MemberGetListActivity.class));
            }
        });

        findViewById(R.id.iv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, QrCodeActivity.class));
            }
        });
        findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cjHistory != null) {
                    if (cjHistory.codeStatus.equals("0")) {
                        exchange();
                    } else {
                        Toast.makeText(context, "已兑换过了", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "没有兑换信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getCjQrDetail(token, detailId), new Subscriber<Response<CjHistory>>() {
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
            public void onNext(Response<CjHistory> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    cjHistory = arrayListResponse.data;
                    setData();

                }
            }
        });
    }

    void exchange(){
        HttpMethods.start(HttpMethods.getInstance().demoService.AwardExchange(token, detailId, cjHistory.redeemCode), new Subscriber<Response>() {
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

    long getDateLong(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(str);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
