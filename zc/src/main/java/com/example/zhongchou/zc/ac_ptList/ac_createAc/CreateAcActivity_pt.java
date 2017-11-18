package com.example.zhongchou.zc.ac_ptList.ac_createAc;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhongchou.R;
import com.example.zhongchou.model.Response;
import com.example.zhongchou.module.base.BaseActivity;
import com.example.zhongchou.network.retrofit.HttpMethods;
import com.example.zhongchou.zc.ac_ptList.AcInfoActivity_pt;
import com.example.zhongchou.zc.ac_staffSend.m.Activity_pt;
import com.example.zhongchou.zc.ac_staffSend.m.Goods_pt;
import com.example.zhongchou.zc.widget.CarouselView.CarouselView;
import com.example.zhongchou.utils.ACache;
import com.example.zhongchou.utils.ACacheKey;
import com.example.zhongchou.utils.ScreenUtils;
import com.example.zhongchou.utils.UiUtil;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(CreateAcPresenter_pt.class)
public class CreateAcActivity_pt extends BaseActivity<CreateAcPresenter_pt> {
    public static CreateAcActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    public Activity_pt activity_pt;

    TextView tv_name;
    TextView tv_price;
    TextView tv_oldPrice;
    TextView tv_soldNum;
    TextView tv_leftTime;
    TextView tv_shop;
    TextView tv_address;
    TextView tv_availableTime;
    TextView tv_tip;
    CarouselView cv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_ac_pt;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("新增活动");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("编辑");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_oldPrice = (TextView) findViewById(R.id.tv_oldPrice);
        tv_soldNum = (TextView) findViewById(R.id.tv_soldNum);
        tv_leftTime = (TextView) findViewById(R.id.tv_leftTime);
        tv_shop = (TextView) findViewById(R.id.tv_shop);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_availableTime = (TextView) findViewById(R.id.tv_availableTime);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        cv = (CarouselView) findViewById(R.id.cv);
        cv.getLayoutParams().height = ScreenUtils.getScreenWidth();
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        activity_pt = (Activity_pt) getIntent().getSerializableExtra("ac");

        if (activity_pt != null) {
            setData();
        } else {
            activity_pt = new Activity_pt();
        }
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddGoodsActivity_pt.class));
            }
        });

    }

    public void setData(){
        if(activity_pt.endTime!=null)
            tv_leftTime.setText(activity_pt.endTime);
        if(activity_pt.storeName!=null)
            tv_shop.setText(activity_pt.storeName);
        if(activity_pt.merchantAddress!=null)
            tv_address.setText(activity_pt.merchantAddress);
        if (activity_pt.beginTime != null && activity_pt.endTime != null)
            tv_availableTime.setText("有效期：" + activity_pt.beginTime + " 至 " + activity_pt.endTime);
        if(activity_pt.saleRemarks!=null)
            tv_tip.setText(activity_pt.saleRemarks);
        if(activity_pt.saleNum!=null)
            tv_soldNum.setText("已售" + activity_pt.saleNum);

        if (activity_pt.ptGood != null) {
            if (activity_pt.ptGood.name != null)
                tv_name.setText(activity_pt.ptGood.name);
            if (activity_pt.ptGood.price != 0)
                tv_price.setText("" + activity_pt.ptGood.price);
            if (activity_pt.ptGood.originalPrice != null)
                tv_oldPrice.setText("原价￥" + activity_pt.ptGood.originalPrice);
            if (activity_pt.ptGood.imgUrl != null) {
                if (!activity_pt.ptGood.imgUrl.equals("")) {
                    String[] imgs = activity_pt.ptGood.imgUrl.split(",");
                    if (imgs.length != 0) {
                        setCv(imgs);
                    }
                }
            }
        } else {
            activity_pt.ptGood = new Goods_pt();
        }
    }

    void setCv(String[] strArray){
        cv.setAdapter(new CarouselView.Adapter() {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public View getView(final int position) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_cv_img, null);
                ImageView iv = (ImageView) view.findViewById(R.id.iv_cv);
                UiUtil.setImage(iv, strArray[position]);

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context, AddGoodsActivity_pt.class).putExtra("goods",activity_pt.ptGood));
                    }
                });
                return view;
            }

            @Override
            public int getCount() {
                return strArray.length;
            }
        });
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.ll_addAc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddAcActivity_pt.class));
            }
        });
        findViewById(R.id.ll_setStore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, StoreListActivity_pt.class));
            }
        });
        findViewById(R.id.ll_setTip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddTipActivity.class));
            }
        });
        findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_pt.name == null || activity_pt.imgUrl == null || activity_pt.beginTime == null || activity_pt.endTime == null) {
                    Toast.makeText(context, "活动信息不完整", Toast.LENGTH_SHORT).show();
                } else if (activity_pt.ptGood.name == null || activity_pt.ptGood.originalPrice == null || activity_pt.ptGood.imgUrl == null) {
                    Toast.makeText(context, "商品信息不完整", Toast.LENGTH_SHORT).show();
                } else if (activity_pt.saleRemarks == null || activity_pt.saleRemarks.isEmpty()) {
                    Toast.makeText(context, "未设置消费提示", Toast.LENGTH_SHORT).show();
                } else if (activity_pt.storeId == null) {
                    Toast.makeText(context, "未设置门店", Toast.LENGTH_SHORT).show();
                } else {
                    if (activity_pt.id == null) {
                        addAc();
                    } else {
                        editAc();
                    }
                }
            }
        });
    }

    void addAc(){
        HttpMethods.start(HttpMethods.getInstance().demoService.addAc(token, activity_pt.ptGood.name, activity_pt.ptGood.originalPrice, activity_pt.ptGood.price + "", activity_pt.ptGood.count + "",
                activity_pt.ptGood.videoUrl, activity_pt.ptGood.imgUrl, activity_pt.name, activity_pt.imgUrl, activity_pt.beginTime, activity_pt.endTime, activity_pt.num + "",
                activity_pt.saleNum, activity_pt.storeId, activity_pt.saleRemarks), new Subscriber<Response>() {
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
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                    if (AcInfoActivity_pt.instance != null) {
                        AcInfoActivity_pt.instance.getData();
                    }
                    finish();
                }
            }
        });
    }
    void editAc(){
        HttpMethods.start(HttpMethods.getInstance().demoService.editAc(token, activity_pt.id, activity_pt.ptGood.name, activity_pt.ptGood.originalPrice, activity_pt.ptGood.price + "", activity_pt.ptGood.count + "",
                activity_pt.ptGood.videoUrl, activity_pt.ptGood.imgUrl, activity_pt.name, activity_pt.imgUrl, activity_pt.beginTime, activity_pt.endTime, activity_pt.num + "",
                activity_pt.saleNum, activity_pt.storeId, activity_pt.saleRemarks), new Subscriber<Response>() {
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
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    if (AcInfoActivity_pt.instance != null) {
                        AcInfoActivity_pt.instance.getData();
                    }
                    finish();
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
