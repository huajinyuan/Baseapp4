package com.example.huaxiang.hx;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.AcListActivity_pt;
import com.example.huaxiang.hx.ac_bb.AcBbActivity;
import com.example.huaxiang.hx.ac_bb.IntentionCustomerActivity;
import com.example.huaxiang.hx.ac_bb.RebackListActivity;
import com.example.huaxiang.hx.ac_bb.StaffRankingActivity;
import com.example.huaxiang.hx.ac_memberget.MemberGetActivity;
import com.example.huaxiang.hx.ac_staffSend.StaffSendActivity_pt;
import com.example.huaxiang.hx.ac_withdrawSetting.SettingActivity_pt;
import com.example.huaxiang.hx.adapter.AppMenuAdapter;
import com.example.huaxiang.hx.m.AppMenu;
import com.example.huaxiang.hx.m.LoginData_pt;
import com.example.huaxiang.hx.m.Report_hx;
import com.example.huaxiang.hx.m.ShareData;
import com.example.huaxiang.hx.utils.DisplayMetricsUtil;
import com.example.huaxiang.hx.utils.RvDialogSelectAdapter;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;
import com.example.huaxiang.utils.AppContext;

import java.util.ArrayList;

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

    TextView tv_totalManCount;
    TextView tv_totalReplaceCount;
    TextView tv_intentionCount;
    TextView tv_conversionCount;

    int requestStatus;

    String username, password, sysSign;
    SwipeRefreshLayout swip_refresh;

    RecyclerView rv_menu1;
    RecyclerView rv_menu2;
    LoginData_pt loginData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_hx;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        ShareData.mainActivity = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("用户画像");
        tv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        tv_totalManCount = (TextView) findViewById(R.id.tv_totalManCount);
        tv_totalReplaceCount = (TextView) findViewById(R.id.tv_totalReplaceCount);
        tv_intentionCount = (TextView) findViewById(R.id.tv_intentionCount);
        tv_conversionCount = (TextView) findViewById(R.id.tv_conversionCount);

        rv_menu1 = findView(R.id.rv_menu1);
        rv_menu2 = findView(R.id.rv_menu2);

        getPermissions(this);

        swip_refresh = findView(R.id.swip_refresh);
        swip_refresh.setColorSchemeResources(R.color.colorAppRed, R.color.colorMyGreen, R.color.colorMyBlue);
        swip_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

    }

    @Override
    protected void initData() {
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        sysSign = getIntent().getStringExtra("sysSign");

        if (password != null) {
            login();
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
        findViewById(R.id.ll_hx_ac_bb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AcBbActivity.class));
            }
        });
        findViewById(R.id.ll_intention).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, IntentionCustomerActivity.class));
            }
        });
        findViewById(R.id.ll_pt_ptreback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RebackListActivity.class));
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

        findViewById(R.id.ll_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, IntentionCustomerActivity.class));
            }
        });
        findViewById(R.id.ll_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, IntentionCustomerActivity.class));
            }
        });
        findViewById(R.id.ll_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, IntentionCustomerActivity.class));
            }
        });
        findViewById(R.id.ll_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RebackListActivity.class));
            }
        });

    }

    void setReport(Report_hx report_hx) {
        if (report_hx.totalManCount == null || report_hx.totalManCount.equals("")) {
            tv_totalManCount.setText("0");
        } else {
            tv_totalManCount.setText(report_hx.totalManCount + "");
        }
        if (report_hx.totalReplaceCount == null || report_hx.totalReplaceCount.equals("")) {
            tv_totalReplaceCount.setText("0");
        } else {
            tv_totalReplaceCount.setText(report_hx.totalReplaceCount + "");
        }
        if (report_hx.intentionCount == null || report_hx.intentionCount.equals("")) {
            tv_intentionCount.setText("0");
        } else {
            tv_intentionCount.setText(report_hx.intentionCount + "");
        }
        if (report_hx.conversionCount == null || report_hx.conversionCount.equals("")) {
            tv_conversionCount.setText("0");
        } else {
            tv_conversionCount.setText(report_hx.conversionCount + "");
        }
    }

    void setMenu(){
        ArrayList<AppMenu> menus1 = new ArrayList<>();
        ArrayList<AppMenu> menus2 = new ArrayList<>();
        for (AppMenu appMenu : loginData.menuList) {
            if (appMenu.permission.startsWith("zt:hx:")) {
                if (appMenu.sort.length() == 2) {
                    menus1.add(appMenu);
                } else if (appMenu.sort.length() == 3) {
                    menus2.add(appMenu);
                }
            }
        }
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_menu1.setLayoutManager(layoutManager1);
        rv_menu1.setAdapter(new AppMenuAdapter(context,menus1));

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_menu2.setLayoutManager(layoutManager2);
        rv_menu2.setAdapter(new AppMenuAdapter(context,menus2));
    }

    void refresh(){
        tv_totalManCount.setText("0");
        tv_totalReplaceCount.setText("0");
        tv_intentionCount.setText("0");
        tv_conversionCount.setText("0");
        getReport();
    }

    void login() {
        HttpMethods.start(HttpMethods.getInstance().demoService.doLogin_pt(username, password, sysSign), new Subscriber<Response<LoginData_pt>>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa login", e.toString() + "");
            }

            @Override
            public void onNext(Response<LoginData_pt> logdResponse) {
                if (logdResponse != null) {
                    if (logdResponse.code != 0) {
                        Toast.makeText(context, logdResponse.message, Toast.LENGTH_SHORT).show();
                    } else {
                        loginData = logdResponse.data;
                        if (loginData.menuList != null) {
                            setMenu();
                        }
                        aCache.put(ACacheKey.TOKEN, logdResponse.data.getToken());
                        token = logdResponse.data.getToken();
                        getReport();
                        Log.e("aaa==onNext==Token:", token);
                    }
                } else {
                    Log.e("=======onNext", logdResponse.message);
                }
            }
        });
    }

    void getReport() {
        HttpMethods.start(HttpMethods.getInstance().demoService.getReport_hx(token, requestStatus), new Subscriber<Response<Report_hx>>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("aaa", "getReport onStart");
                swip_refresh.setRefreshing(true);
            }

            @Override
            public void onCompleted() {
                Log.e("aaa", "getReport onCompleted");
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onNext(Response<Report_hx> response) {
                if (response.data !=null) {
                    setReport(response.data);
                    Log.e("aaa======onNext", response.data.toString());
                } else {
                    Log.e("aaa======onNext", response.message);
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
                    ShareData.status_main = position;
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