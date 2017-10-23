package com.example.huaxiang.hx.ac_staffSend;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.AppContext;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(AddShopPresenter_pt.class)
public class AddShopActivity_pt extends BaseActivity<AddShopPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_shop_pt;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("新增门店");
        tv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

    }

    @Override
    protected void initData() {
    }


    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        findViewById(R.id.ll_pt_acList).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, BbActivityActivity.class));
//            }
//        });
    }


//    void login(){
//        HttpMethods.getInstance().login("shanghu2", "123456").subscribe(new Subscriber<Response<LoginData_pt>>(){
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                Log.e("=============", "onStart");
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.e("=============", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("=======onError", e.toString() + "");
//            }
//
//            @Override
//            public void onNext(Response<LoginData_pt> logdResponse) {
//                if (logdResponse.code==0){
//                    aCache.put(ACacheKey.TOKEN, logdResponse.data.getToken());
//                    token = logdResponse.data.getToken();
//                    getReport_pt();
//                    Log.e("aaa========Token:", token);
//
//                }else {
//                    Log.e("=======onNext", logdResponse.msg);
//                }
//            }
//        });
//    }
//    void getReport_pt(int status){
//        HttpMethods.getInstance().getReport_pt(token, status).subscribe(new Subscriber<Response<PtReport_pt>>() {
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                Log.e("aaa", "onStart");
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.e("aaa", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("aaa======onError", e.toString() + "");
//            }
//
//            @Override
//            public void onNext(Response<PtReport_pt> response) {
//                if (response.code == 0) {
//                    setReport(response.data);
//                    Log.e("aaa======onNext", response.data.toString());
//                } else {
//                    Log.e("aaa======onNext", response.msg);
//                }
//            }
//        });
//    }


}
