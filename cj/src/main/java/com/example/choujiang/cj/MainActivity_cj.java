package com.example.choujiang.cj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_acSetting.AcListActivity_cj;
import com.example.choujiang.cj.ac_cjbb.AcBbActivity;
import com.example.choujiang.cj.ac_cjbb.CjHistoryActivity;
import com.example.choujiang.cj.ac_cjbb.StaffRankingActivity;
import com.example.choujiang.cj.ac_memberget.MemberGetActivity;
import com.example.choujiang.cj.ac_staffSend.StaffSendActivity_pt;
import com.example.choujiang.cj.ac_withdrawSetting.SettingActivity_cj;
import com.example.choujiang.cj.m.LoginData_pt;
import com.example.choujiang.cj.m.Report_cj;
import com.example.choujiang.cj.utils.DisplayMetricsUtil;
import com.example.choujiang.cj.utils.RvDialogSelectAdapter;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;
import com.example.choujiang.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(MainPresenter_cj.class)
public class MainActivity_cj extends BaseActivity<MainPresenter_cj> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    TextView tv_totalManCount;
    TextView tv_totalShareCount;
    TextView tv_totalCount;
    TextView tv_totalAwardCount;

    int requestStatus;

    String username, password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_cj;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("幸运抽奖");
        tv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        tv_totalManCount = (TextView) findViewById(R.id.tv_totalManCount);
        tv_totalShareCount = (TextView) findViewById(R.id.tv_totalShareCount);
        tv_totalCount = (TextView) findViewById(R.id.tv_totalCount);
        tv_totalAwardCount = (TextView) findViewById(R.id.tv_totalAwardCount);

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
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        if (password != null) {
            login();
        } else {
            if (aCache.getAsString(ACacheKey.TOKEN) == null) {
                Toast.makeText(context, "没有登录记录", Toast.LENGTH_SHORT).show();
            } else {
                token = aCache.getAsString(ACacheKey.TOKEN);
                Log.e("aaa", token);
                getReport();
            }
        }
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        findViewById(R.id.iv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSelect();
            }
        });
        findViewById(R.id.ll_pt_acList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AcBbActivity.class));
            }
        });
        findViewById(R.id.ll_pt_ptHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CjHistoryActivity.class));
            }
        });
        findViewById(R.id.ll_pt_staffRanking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffRankingActivity.class));
            }
        });
        findViewById(R.id.ll_pt_pt_ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AcListActivity_cj.class));
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
                context.startActivity(new Intent(context, SettingActivity_cj.class));
            }
        });
        findViewById(R.id.ll_pt_staffSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffSendActivity_pt.class));
            }
        });
    }

    void setReport(Report_cj report_cj) {
        tv_totalManCount.setText(report_cj.totalManCount + "");
        tv_totalShareCount.setText(report_cj.totalShareCount + "");
        tv_totalCount.setText(report_cj.totalCount + "");
        tv_totalAwardCount.setText(report_cj.totalAwardCount + "");
    }

    void login(){
        HttpMethods.getInstance().login(username, password).subscribe(new Subscriber<Response<LoginData_pt>>(){
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
    void getReport(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getReport_pt(token, requestStatus), new Subscriber<Response<Report_cj>>() {
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
                Toast.makeText(context, "已在其他设备登录", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Response<Report_cj> response) {
                if (response.code == 0) {
                    Log.e("aaa======onNext", response.data.toString());
                    setReport(response.data);
                } else {
                    Log.e("aaa======onNext", response.msg);
                }
            }
        });
    }

    void showDialogSelect() {
        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogSelect);
        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view_dialog = LayoutInflater.from(context).inflate(R.layout.item_dialog_select, null);
        dialog.setContentView(view_dialog);

        //->
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.y = DisplayMetricsUtil.dip2px(context, 50);
        params.width = DisplayMetricsUtil.getScreenWidth(context);
        window.setAttributes(params);
        //->

        RecyclerView rv_dialog = (RecyclerView) view_dialog.findViewById(R.id.rv_dialog_select);
        LinearLayoutManager selectLayoutManager = new LinearLayoutManager(context);
        rv_dialog.setLayoutManager(selectLayoutManager);
        ArrayList<String> selectData = new ArrayList<>();
        selectData.add("全部");
        selectData.add("本日");
        selectData.add("本月");
        selectData.add("本年");
        RvDialogSelectAdapter selectAdapter = new RvDialogSelectAdapter(context, selectData);
        rv_dialog.setAdapter(selectAdapter);

        selectAdapter.setSelectPosition(requestStatus);
        selectAdapter.SetSelectListener(new RvDialogSelectAdapter.SelectListener() {
            @Override
            public void select(int position) {
                if (requestStatus != position) {
                    requestStatus = position;
                    getReport();
                }
                dialog.dismiss();
            }
        });
    }

}