package com.zt.pintuan.pt.ac_withdrawSetting;

import android.app.AlertDialog;
import android.content.Context;
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

import com.zt.pintuan.R;
import com.zt.pintuan.model.Response;
import com.zt.pintuan.module.base.BaseActivity;
import com.zt.pintuan.network.retrofit.HttpMethods;
import com.zt.pintuan.pt.ac_ptList.m.Store_cj;
import com.zt.pintuan.pt.ac_staffSend.m.Staff_pt;
import com.zt.pintuan.pt.ac_withdrawSetting.adapter.AccountListAdapter_pt;
import com.zt.pintuan.pt.utils.DisplayMetricsUtil;
import com.zt.pintuan.pt.utils.RvDialogSelectAdapter;
import com.zt.pintuan.utils.ACache;
import com.zt.pintuan.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(AccountListPresenter_pt.class)
public class AccountListActivity_pt extends BaseActivity<AccountListPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_accountList;
    AccountListAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Staff_pt> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;
    SwipeRefreshLayout swip_refresh;

    ArrayList<Store_cj> stores = new ArrayList<>();
    int requestStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_list_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("账户列表");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_accountList = (RecyclerView) findViewById(R.id.rv_accountList);

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

        Store_cj storeNull = new Store_cj();
        storeNull.storeName = "全部";
        stores.add(storeNull);
        getStore();

        rv_accountList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
            pinDan_pts.clear();
            pinDan_pts.addAll(pinDans);
            adapter = new AccountListAdapter_pt(context, pinDan_pts);
            layoutManager = new LinearLayoutManager(context);
            rv_accountList.setLayoutManager(layoutManager);
            rv_accountList.setAdapter(adapter);
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
    }

    void getData(){
        if (stores.size() > 0) {
            HttpMethods.start(HttpMethods.getInstance().demoService.getStaff_cj(token, page, 10, stores.get(requestStatus).id), new Subscriber<Response<ArrayList<Staff_pt>>>() {
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
                public void onNext(Response<ArrayList<Staff_pt>> arrayListResponse) {
                    if (arrayListResponse.data != null) {
                        setRv(arrayListResponse.data);
                        canGet = true;
                        page++;
                    }
                }
            });
        } else {
            swip_refresh.setRefreshing(false);
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

}
