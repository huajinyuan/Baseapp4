package com.example.zhongchou.zc;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
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

import com.example.zhongchou.R;
import com.example.zhongchou.model.Response;
import com.example.zhongchou.module.base.BaseActivity;
import com.example.zhongchou.network.retrofit.HttpMethods;
import com.example.zhongchou.zc.ac_memberget.MemberGetActivity;
import com.example.zhongchou.zc.ac_ptList.AcListActivity_pt;
import com.example.zhongchou.zc.ac_ptbb.AcBbActivity_pt;
import com.example.zhongchou.zc.ac_ptbb.PtListActivity_pt;
import com.example.zhongchou.zc.ac_ptbb.StaffRankingActivity_pt;
import com.example.zhongchou.zc.ac_staffSend.StaffSendActivity_pt;
import com.example.zhongchou.zc.ac_withdrawSetting.SettingActivity_pt;
import com.example.zhongchou.zc.m.LoginData_pt;
import com.example.zhongchou.zc.m.PtReport_pt;
import com.example.zhongchou.zc.utils.DisplayMetricsUtil;
import com.example.zhongchou.zc.utils.RvDialogSelectAdapter;
import com.example.zhongchou.utils.ACache;
import com.example.zhongchou.utils.ACacheKey;
import com.example.zhongchou.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(MainPresenter_pt.class)
public class MainActivity_pt extends BaseActivity<MainPresenter_pt> {
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

    int requestStatus;

    String username, password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_zc;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("众筹");
        tv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        tv_drivingTurnover = (TextView) findViewById(R.id.tv_drivingTurnover);
        tv_groupSize = (TextView) findViewById(R.id.tv_groupSize);
        tv_cliqueNumber = (TextView) findViewById(R.id.tv_cliqueNumber);
        tv_spellTogether = (TextView) findViewById(R.id.tv_spellTogether);

        getPermissions(this);
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
                finish();
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
                context.startActivity(new Intent(context, AcBbActivity_pt.class));
            }
        });
        findViewById(R.id.ll_pt_ptHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PtListActivity_pt.class));
            }
        });
        findViewById(R.id.ll_pt_staffRanking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffRankingActivity_pt.class));
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
        tv_drivingTurnover.setText(ptReport_pt.drivingTurnover + "");
        tv_groupSize.setText(ptReport_pt.orderCount + "");
        tv_cliqueNumber.setText(ptReport_pt.completedCount + "");
        tv_spellTogether.setText(ptReport_pt.awaitCount + "");
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
        HttpMethods.start(HttpMethods.getInstance().demoService.getReport_pt(token, requestStatus), new Subscriber<Response<PtReport_pt>>() {
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
            public void onNext(Response<PtReport_pt> response) {
                if (response.data !=null) {
                    setReport(response.data);
                    Log.e("aaa======onNext", response.data.toString());
                } else {
                    Log.e("aaa======onNext", response.msg);
                }
            }
        });
    }

    void refresh(){
        tv_drivingTurnover.setText("0");
        tv_groupSize.setText("0");
        tv_cliqueNumber.setText("0");
        tv_spellTogether.setText("0");
        getReport();
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
                    refresh();
                }
                dialog.dismiss();
            }
        });
    }

    void getPermissions(Activity mActivity){
        String[] PERMISSIONS_STORAGE = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.CAMERA"};
        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("permission", ">23");
            try {
                //检测是否有写的权限
                int permissionCAMERA = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA);
                int permissionSD = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCAMERA != PackageManager.PERMISSION_GRANTED || permissionSD != PackageManager.PERMISSION_GRANTED) {
                    Log.e("permission", "permissionCAMERA:" + permissionCAMERA + " " + "permissionSD:" + permissionSD);
                    // 没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(mActivity, PERMISSIONS_STORAGE, 1);
                } else {
                    Log.e("permission", "permission 2");
                }
            } catch (Exception e) {
                Log.e("permission", "permission 3" + e.toString());
                e.printStackTrace();
            }
        }
    }


}
