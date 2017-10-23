package com.example.huaxiang.hx.ac_staffSend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_staffSend.adapter.ShopAdapter_pt;
import com.example.huaxiang.hx.ac_staffSend.m.Shop_pt;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.AppContext;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(ShopPresenter_pt.class)
public class ShopActivity_pt extends BaseActivity<ShopPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_shop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_pt;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("门店管理");
        tv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_shop = (RecyclerView) findViewById(R.id.rv_shop);
    }

    @Override
    protected void initData() {
        ArrayList<Shop_pt> shops = new ArrayList<>();
        Shop_pt shop = new Shop_pt();
        shop.name = "众途汽车美容维修一号店";
        shop.phone = "13866668888";
        shop.address = "厦门市思明区软件园";
        for (int i=0;i<10;i++) {
            shops.add(shop);
        }
        ShopAdapter_pt adapter = new ShopAdapter_pt(context, shops);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_shop.setLayoutManager(layoutManager);
        rv_shop.setAdapter(adapter);
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
                context.startActivity(new Intent(context, AddShopActivity_pt.class));
            }
        });
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


}
