package com.zt.pintuan.pt.ac_staffSend.addStaffSend;

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

import com.zt.pintuan.R;
import com.zt.pintuan.model.Response;
import com.zt.pintuan.module.base.BaseActivity;
import com.zt.pintuan.network.retrofit.HttpMethods;
import com.zt.pintuan.pt.ac_staffSend.addStaffSend.adapter.SelectAcAdapter_pt;
import com.zt.pintuan.pt.ac_staffSend.m.Activity_pt;
import com.zt.pintuan.pt.ac_staffSend.staffDetail.StaffDetailActivity_pt;
import com.zt.pintuan.pt.utils.DisplayMetricsUtil;
import com.zt.pintuan.pt.utils.RvDialogSelectAdapter;
import com.zt.pintuan.utils.ACache;
import com.zt.pintuan.utils.ACacheKey;
import com.zt.pintuan.utils.ToastUtil;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(SelectAcPresenter_pt.class)
public class SelectAcActivity_pt extends BaseActivity<SelectAcPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    SelectAcAdapter_pt adapter;
    String userId;

    LinearLayoutManager layoutManager;
    ArrayList<Activity_pt> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;
    int requestStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_ac_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("选择团购活动");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        userId = getIntent().getStringExtra("userId");
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

    void setRv(ArrayList<Activity_pt> pinDans) {
        if (adapter == null) {
            pinDan_pts.clear();
            pinDan_pts.addAll(pinDans);
            adapter = new SelectAcAdapter_pt(context, pinDan_pts);
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
        findViewById(R.id.bt_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    String acIds = "";
                    for (Activity_pt activity_pt : adapter.data) {
                        if (activity_pt.checked) {
                            acIds += activity_pt.id + ",";
                        }
                    }
                    if (!acIds.equals("")) {
                        saveData(acIds, userId);
                    }
                }
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getAc_pt(token, page, 10, requestStatus), new Subscriber<Response<ArrayList<Activity_pt>>>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
                canGet = false;
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<ArrayList<Activity_pt>> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    setRv(arrayListResponse.data);
                    canGet = true;
                    page++;
                }
            }
        });
    }

    void saveData(String activityIds, String userId) {
        HttpMethods.start(HttpMethods.getInstance().demoService.saveStaffSend(token, activityIds, userId), new Subscriber<Response>() {
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
                    ToastUtil.showToast("提交成功");
                    finish();
                    if (SelectStaffActivity_pt.instance != null) {
                        SelectStaffActivity_pt.instance.finish();
                    }
                    if (StaffDetailActivity_pt.instance==null){
                        startActivity(new Intent(context, StaffDetailActivity_pt.class).putExtra("userId", userId));
                    }
                } else {
                    ToastUtil.showToast(arrayListResponse.msg);
                }
            }
        });
    }

    void refresh(){
        pinDan_pts.clear();
        if(adapter!=null)
            adapter.notifyDataSetChanged();
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
