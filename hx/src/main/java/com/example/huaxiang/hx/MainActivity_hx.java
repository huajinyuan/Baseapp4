package com.example.huaxiang.hx;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_memberget.MemberGetActivity;
import com.example.huaxiang.hx.ac_ptList.AcListActivity_pt;
import com.example.huaxiang.hx.ac_ptbb.BbActivityActivity;
import com.example.huaxiang.hx.ac_ptbb.MemberListActivity;
import com.example.huaxiang.hx.ac_ptbb.PdListActivity;
import com.example.huaxiang.hx.ac_staffSend.StaffSendActivity_pt;
import com.example.huaxiang.hx.ac_withdrawSetting.SettingActivity_pt;
import com.example.huaxiang.hx.m.LoginData_pt;
import com.example.huaxiang.hx.m.PtReport_pt;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;
import com.example.huaxiang.utils.AppContext;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(MainPresenter_hx.class)
public class MainActivity_hx extends BaseActivity<MainPresenter_hx> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    TextView tv_drivingTurnover;
    TextView tv_groupSize;
    TextView tv_cliqueNumber;
    TextView tv_spellTogether;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pt_index;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("拼团");
        tv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        tv_drivingTurnover = (TextView) findViewById(R.id.tv_drivingTurnover);
        tv_groupSize = (TextView) findViewById(R.id.tv_groupSize);
        tv_cliqueNumber = (TextView) findViewById(R.id.tv_cliqueNumber);
        tv_spellTogether = (TextView) findViewById(R.id.tv_spellTogether);

//        ResponseHandle.IRetryListener listener = new ResponseHandle.IRetryListener() {
//            @Override
//            public Func1<Observable<? extends Throwable>, Observable<?>> retry() {
//                Log.e("=========","==============");
//                return null;
//            }
//        };
//        ResponseHandle.setRetryListener(listener);


    }

    @Override
    protected void initData() {
        if (aCache.getAsString(ACacheKey.TOKEN) == null) {
            login();
        } else {
            token = aCache.getAsString(ACacheKey.TOKEN);
            Log.e("aaa", token);
            getReport();
        }
    }

    void getReport(){
        getReport(0);
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        findViewById(R.id.ll_pt_acList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BbActivityActivity.class));
            }
        });
        findViewById(R.id.ll_pt_ptHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PdListActivity.class));
            }
        });
        findViewById(R.id.ll_pt_staffRanking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MemberListActivity.class));
            }
        });
        findViewById(R.id.ll_pt_pt_ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AcListActivity_pt.class));
            }
        });
        findViewById(R.id.ll_pt_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MemberGetActivity.class));
            }
        });
        findViewById(R.id.ll_pt_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SettingActivity_pt.class));
            }
        });
        findViewById(R.id.ll_pt_staffSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffSendActivity_pt.class));
            }
        });
    }

    void setReport(PtReport_pt ptReport_pt) {
        tv_drivingTurnover.setText(ptReport_pt.getDrivingTurnover() + "");
        tv_groupSize.setText(ptReport_pt.getGroupSize() + "");
        tv_cliqueNumber.setText(ptReport_pt.getCliqueNumber() + "");
        tv_spellTogether.setText(ptReport_pt.getSpellTogether() + "");
    }

    void login(){
        HttpMethods.getInstance().login("shanghu2", "123456").subscribe(new Subscriber<Response<LoginData_pt>>(){
            @Override
            public void onStart() {
                super.onStart();
                Log.e("=============", "onStart");
            }

            @Override
            public void onCompleted() {
                Log.e("=============", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("=======onError", e.toString() + "");
            }

            @Override
            public void onNext(Response<LoginData_pt> logdResponse) {
                if (logdResponse.code==0){
                    aCache.put(ACacheKey.TOKEN, logdResponse.data.getToken());
                    token = logdResponse.data.getToken();
                    getReport();
                    Log.e("aaa========Token:", token);

                }else {
                    Log.e("=======onNext", logdResponse.msg);
                }
            }
        });
    }
    void getReport(int status){
        HttpMethods.getInstance().getReport(token, status).subscribe(new Subscriber<Response<PtReport_pt>>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.e("aaa", "onStart");
            }

            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa======onError", e.toString() + "");
            }

            @Override
            public void onNext(Response<PtReport_pt> response) {
                if (response.code == 0) {
                    setReport(response.data);
                    Log.e("aaa======onNext", response.data.toString());
                } else {
                    Log.e("aaa======onNext", response.msg);
                }
            }
        });
    }


}