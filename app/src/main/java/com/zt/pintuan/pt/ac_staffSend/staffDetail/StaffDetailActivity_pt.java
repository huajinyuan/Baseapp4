package com.zt.pintuan.pt.ac_staffSend.staffDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.model.Response;
import com.zt.pintuan.module.base.BaseActivity;
import com.zt.pintuan.network.retrofit.HttpMethods;
import com.zt.pintuan.pt.ac_staffSend.addStaffSend.SelectAcActivity_pt;
import com.zt.pintuan.pt.ac_staffSend.m.Activity_pt;
import com.zt.pintuan.pt.ac_staffSend.staffDetail.adapter.StaffDetailAdapter_pt;
import com.zt.pintuan.pt.utils.BitmapUtil;
import com.zt.pintuan.pt.utils.DisplayMetricsUtil;
import com.zt.pintuan.utils.ACache;
import com.zt.pintuan.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(StaffDetailPresenter_pt.class)
public class StaffDetailActivity_pt extends BaseActivity<StaffDetailPresenter_pt> {
    public static StaffDetailActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_staffSend;
    ImageView iv_qr_bottom;
    String userId;

    StaffDetailAdapter_pt adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Activity_pt> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;
    SwipeRefreshLayout swip_refresh;

    int touchDownRawX, touchDownRawY, touchUpRawY;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_detail_pt;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("员工二维码");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("设置");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);
        iv_qr_bottom = (ImageView) findViewById(R.id.iv_qr_bottom);

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
        userId = getIntent().getStringExtra("userId");
        getData();

    }

    void setRv(ArrayList<Activity_pt> activity_pts) {
        StaffDetailAdapter_pt adapter = new StaffDetailAdapter_pt(context, activity_pts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_staffSend.setLayoutManager(layoutManager);
        rv_staffSend.setAdapter(adapter);
    }


    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SelectAcActivity_pt.class).putExtra("userId", userId));
            }
        });
        setDrag(iv_qr_bottom);
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getStaffDetail_pt(token, page, 10, 0, userId), new Subscriber<Response<ArrayList<Activity_pt>>>() {
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
            public void onNext(Response<ArrayList<Activity_pt>> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    setRv(arrayListResponse.data);
                    canGet = true;
                    page++;
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

    void setDrag(ImageView iv){
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        touchDownRawX = (int) motionEvent.getRawX();
                        touchDownRawY = (int) motionEvent.getRawY();
                        Log.e("aaa  touchDownRawY", touchDownRawY + "");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchUpRawY=(int) motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("aaa  touchUpRawY", touchUpRawY + "");
                        if (touchDownRawY - touchUpRawY > 10) {
                            popUp();
                        }
                        break;
                }
                return true;
            }
        });
    }

    void popUp(){
        iv_qr_bottom.setVisibility(View.INVISIBLE);

        QrPopWin_pt popWin_pt = new QrPopWin_pt(context);
        popWin_pt.showAtLocation(findViewById(R.id.staff_detail_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

        popWin_pt.setCloseListener(new QrPopWin_pt.CloseListener() {
            @Override
            public void close() {
                iv_qr_bottom.setVisibility(View.VISIBLE);
            }
        });
        popWin_pt.setQr(BitmapUtil.createQrCode(userId, DisplayMetricsUtil.dip2px(context, 190), DisplayMetricsUtil.dip2px(context, 190)));
    }

    @Override
    protected void onDestroy() {
        instance=null;
        super.onDestroy();
    }
}
