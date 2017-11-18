package com.zt.pintuan.pt.ac_ptList.ac_createAc;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.model.Response;
import com.zt.pintuan.module.base.BaseActivity;
import com.zt.pintuan.network.retrofit.HttpMethods;
import com.zt.pintuan.pt.ac_ptList.ac_createAc.adapter.StoreListAdapter_pt;
import com.zt.pintuan.pt.ac_ptList.m.Store_cj;
import com.zt.pintuan.utils.ACache;
import com.zt.pintuan.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(StoreListPresenter_pt.class)
public class StoreListActivity_pt extends BaseActivity<StoreListPresenter_pt> {
    public static StoreListActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    RecyclerView rv_shop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_list_pt;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("选择门店");
        tv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        rv_shop = (RecyclerView) findViewById(R.id.rv_shop);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        getStore();
    }

    void setRv(ArrayList<Store_cj> store_cjs) {
        StoreListAdapter_pt adapter = new StoreListAdapter_pt(context, store_cjs);
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
                    setRv(arrayListResponse.data);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
