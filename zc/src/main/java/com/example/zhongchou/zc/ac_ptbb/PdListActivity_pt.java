package com.example.zhongchou.zc.ac_ptbb;

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
import android.widget.Toast;

import com.example.zhongchou.R;
import com.example.zhongchou.model.Response;
import com.example.zhongchou.module.base.BaseActivity;
import com.example.zhongchou.network.retrofit.HttpMethods;
import com.example.zhongchou.zc.ac_ptbb.adapter.PdListAdapter_pt;
import com.example.zhongchou.zc.ac_ptbb.m.PinDan_pt;
import com.example.zhongchou.zc.utils.DisplayMetricsUtil;
import com.example.zhongchou.zc.utils.RvDialogSelectAdapter;
import com.example.zhongchou.utils.ACache;
import com.example.zhongchou.utils.ACacheKey;

import java.util.ArrayList;

import rx.Subscriber;


public class PdListActivity_pt extends BaseActivity<PdListPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;
    TextView tv_topbar_stroke;

    RecyclerView rv_staffSend;
    PdListAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<PinDan_pt> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;
    int requestStatus;
    SwipeRefreshLayout swip_refresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pt_list;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        tv_topbar_stroke = (TextView) findViewById(R.id.tv_topbar_stroke);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("拼单记录");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.VISIBLE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);
        tv_topbar_stroke.setVisibility(View.VISIBLE);

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

    void setRv(ArrayList<PinDan_pt> pinDans) {
        if (adapter == null) {
            pinDan_pts.clear();
            pinDan_pts.addAll(pinDans);
            adapter = new PdListAdapter_pt(context, pinDan_pts);
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
        findViewById(R.id.tv_topbar_stroke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    if (adapter.checkedPosition == -1) {
                        Toast.makeText(context, "未选择活动", Toast.LENGTH_SHORT).show();
                    } else {
                        refund(pinDan_pts.get(adapter.checkedPosition).id);
                    }
                }
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getPdList_pt(token, page, 10, requestStatus), new Subscriber<Response<ArrayList<PinDan_pt>>>() {
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
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onNext(Response<ArrayList<PinDan_pt>> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    setRv(arrayListResponse.data);
                    canGet = true;
                    page++;
                }
            }
        });
    }
    void refund(String id){
        HttpMethods.start(HttpMethods.getInstance().demoService.refundPinDan(token, id), new Subscriber<Response>() {
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
                swip_refresh.setRefreshing(false);
            }

            @Override
            public void onNext(Response arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    Toast.makeText(context, "已提交退款", Toast.LENGTH_SHORT).show();
                    refresh();
                } else {
                    Toast.makeText(context, arrayListResponse.msg, Toast.LENGTH_SHORT).show();
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
        selectData.add("待成团");
        selectData.add("已成团");
        selectData.add("拼团失败");
        selectData.add("退款");
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
