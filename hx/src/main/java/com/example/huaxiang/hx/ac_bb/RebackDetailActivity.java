package com.example.huaxiang.hx.ac_bb;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.adapter.RecordListAdapter;
import com.example.huaxiang.hx.ac_bb.m.Reback_hx;
import com.example.huaxiang.hx.ac_bb.m.Record;
import com.example.huaxiang.hx.utils.DisplayMetricsUtil;
import com.example.huaxiang.hx.utils.RvDialogSelectAdapter;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;

import java.util.ArrayList;

import rx.Subscriber;


public class RebackDetailActivity extends BaseActivity<RebackDetailPresenter> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;
    ImageView iv_topbar_right_detail;

    TextView tv_carNumber;
    RecyclerView rv_staffSend;
    ArrayList<Record> records = new ArrayList<>();

    Reback_hx reback_hx;
    String visitId;
    EditText et_record;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reback_detail_hx;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        iv_topbar_right_detail = (ImageView) findViewById(R.id.iv_topbar_right_detail);
        tv_topbar_title.setText("回访详情");
        tv_topbar_right.setVisibility(View.VISIBLE);
        tv_topbar_right.setText("操作");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        tv_carNumber = findView(R.id.tv_carNumber);
        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

        et_record = (EditText) findView(R.id.et_record);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        visitId = getIntent().getStringExtra("visitId");
        getData();
    }

    void setData(){
        ((TextView) findView(R.id.tv_carNumber)).setText("车牌：" + reback_hx.licensePlate);
        ((TextView) findView(R.id.tv_phone)).setText("手机：" + reback_hx.phone);
        ((TextView) findView(R.id.tv_awardName)).setText("活动：" + reback_hx.actName);
        ((TextView) findView(R.id.tv_staff)).setText("员工：" + reback_hx.user.name);

        if (reback_hx.licensePlate == null || reback_hx.licensePlate.equals("")) {
            tv_carNumber.setVisibility(View.GONE);
        }

        if (reback_hx.createDate != null) {
            ((TextView) findView(R.id.tv_date)).setText(reback_hx.createDate.length() > 10 ? reback_hx.createDate.substring(0, 10) : reback_hx.createDate);
        }

        if (reback_hx.recordList != null) {
            setRv();
        }
    }
    void setRv() {
        RecordListAdapter adapter = new RecordListAdapter(context, reback_hx.recordList);
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
                showDialogSelect();
            }
        });
        findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_record.getText().toString().trim();
                if (!content.isEmpty()) {
                    et_record.setText("");
                    addRecord(content);
                }
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getRebackDetail(token, visitId), new Subscriber<Response<Reback_hx>>() {
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
            public void onNext(Response<Reback_hx> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    reback_hx = arrayListResponse.data;
                    setData();
                }
            }
        });
    }

    void addRecord(String content){
        HttpMethods.start(HttpMethods.getInstance().demoService.addRecord(token, reback_hx.id, content), new Subscriber<Response<Reback_hx>>() {
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
            public void onNext(Response<Reback_hx> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
                    reback_hx = arrayListResponse.data;
                    setData();
                }
            }
        });
    }

    void setRebackIntention(String type){
        HttpMethods.start(HttpMethods.getInstance().demoService.setRebackIntention(token, reback_hx.id, type), new Subscriber<Response<Reback_hx>>() {
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
            public void onNext(Response<Reback_hx> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
                    reback_hx = arrayListResponse.data;
                    setData();
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
        selectData.add("已购买");
        selectData.add("无意向");
        RvDialogSelectAdapter selectAdapter = new RvDialogSelectAdapter(context, selectData);
        rv_dialog.setAdapter(selectAdapter);

        if (reback_hx != null) {
            if (reback_hx.type != null) {
                if (reback_hx.type.equals("2")) {
                    selectAdapter.setSelectPosition(0);
                }
                if (reback_hx.type.equals("3")) {
                    selectAdapter.setSelectPosition(1);
                }
            }
        }

        selectAdapter.SetSelectListener(new RvDialogSelectAdapter.SelectListener() {
            @Override
            public void select(int position) {
                if (reback_hx != null) {
                    if (position == 0) {
                        setRebackIntention("2");
                    }
                    if (position == 1) {
                        setRebackIntention("3");
                    }
                }
                dialog.dismiss();
            }
        });
    }

}
