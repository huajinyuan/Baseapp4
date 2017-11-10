package com.example.huaxiang.hx.ac_acSetting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.CreateAcActivity_cj;
import com.example.huaxiang.hx.ac_acSetting.adapter.AcListAdapter_pt;
import com.example.huaxiang.hx.ac_staffSend.m.Activity_cj;
import com.example.huaxiang.hx.utils.DisplayMetricsUtil;
import com.example.huaxiang.hx.utils.RvDialogSelectAdapter;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;


@RequiresPresenter(AcListPresenter_pt.class)
public class AcListActivity_pt extends BaseActivity<AcListPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    AcListAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Activity_cj> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;
    SwipeRefreshLayout swip_refresh;
    int requestStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ac_list_main;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("活动列表");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

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
        token = aCache.getAsString(ACacheKey.TOKEN);
        getData();

        rv_staffSend.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        if(canGet)
                            getData();
            }
        });
    }

    void setRv(ArrayList<Activity_cj> pinDans) {
        if (adapter == null) {
            pinDan_pts.clear();
            pinDan_pts.addAll(pinDans);
            adapter = new AcListAdapter_pt(context, pinDan_pts);
            layoutManager = new LinearLayoutManager(context);
            rv_staffSend.setLayoutManager(layoutManager);
            rv_staffSend.setAdapter(adapter);
        } else {
            pinDan_pts.addAll(pinDans);
            adapter.notifyDataSetChanged();
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
        findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CreateAcActivity_cj.class));
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getAc_cj(token, page, 5, requestStatus), new Subscriber<Response<ArrayList<Activity_cj>>>() {
            @Override
            public void onStart() {
                super.onStart();
                canGet = false;
                swip_refresh.setRefreshing(true);
            }

            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<ArrayList<Activity_cj>> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    setRv(arrayListResponse.data);
                    canGet = true;
                    page++;
                }
            }
        });
    }

    void refresh(){
        adapter = null;
        page = 1;
        getData();
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
        selectData.add("可用");
        selectData.add("暂停");
        selectData.add("作废");
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

}
