package com.example.zhongchou.zc.ac_staffSend.addStaffSend;

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

import com.example.zhongchou.R;
import com.example.zhongchou.model.Response;
import com.example.zhongchou.module.base.BaseActivity;
import com.example.zhongchou.network.retrofit.HttpMethods;
import com.example.zhongchou.zc.ac_ptList.m.Store_cj;
import com.example.zhongchou.zc.ac_staffSend.addStaffSend.adapter.SelectStaffAdapter_pt;
import com.example.zhongchou.zc.ac_staffSend.m.Staff_pt;
import com.example.zhongchou.zc.utils.DisplayMetricsUtil;
import com.example.zhongchou.zc.utils.RvDialogSelectAdapter;
import com.example.zhongchou.utils.ACache;
import com.example.zhongchou.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(SelectStaffPresenter_pt.class)
public class SelectStaffActivity_pt extends BaseActivity<SelectStaffPresenter_pt> {
    public static SelectStaffActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    SelectStaffAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Staff_pt> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;

    ArrayList<Store_cj> stores = new ArrayList<>();
    int requestStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_staff_pt;
    }

    @Override
    protected void initView() {
        context = this;
        instance = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("选择员工");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);

        Store_cj storeNull = new Store_cj();
        storeNull.storeName = "全部";
        stores.add(storeNull);
        getStore();

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

    void setRv(ArrayList<Staff_pt> pinDans) {
        if (adapter == null) {
            pinDan_pts.addAll(pinDans);
            adapter = new SelectStaffAdapter_pt(context, pinDan_pts);
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
                    startActivity(new Intent(context, SelectAcActivity_pt.class).putExtra("userId", adapter.userId).putExtra("storeId", adapter.storeId));
                }
            }
        });
    }

    void getData(){
        if (stores.size() > 0) {
            HttpMethods.start(HttpMethods.getInstance().demoService.getStaff_cj(token, page, 10, stores.get(requestStatus).id), new Subscriber<Response<ArrayList<Staff_pt>>>() {
                @Override
                public void onStart() {
                    super.onStart();
                    canGet = false;
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
                public void onNext(Response<ArrayList<Staff_pt>> arrayListResponse) {
                    if (arrayListResponse.data != null) {
                        setRv(arrayListResponse.data);
                        canGet = true;
                        page++;
                    }
                }
            });
        } else {

        }
    }

    void getStore(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getStore_cj(token), new Subscriber<Response<ArrayList<Store_cj>>>() {
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
            public void onNext(Response<ArrayList<Store_cj>> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    stores.addAll(arrayListResponse.data);
                    getData();
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
        for (Store_cj store_cj : stores) {
            selectData.add(store_cj.storeName);
        }

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

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
