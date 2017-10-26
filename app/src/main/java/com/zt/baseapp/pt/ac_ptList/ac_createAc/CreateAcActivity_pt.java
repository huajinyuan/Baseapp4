package com.zt.baseapp.pt.ac_ptList.ac_createAc;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zt.baseapp.R;
import com.zt.baseapp.model.Response;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.network.retrofit.HttpMethods;
import com.zt.baseapp.pt.ac_ptList.AcInfoPresenter_pt;
import com.zt.baseapp.pt.ac_ptList.AcListPresenter_pt;
import com.zt.baseapp.pt.ac_staffSend.m.Activity_pt;
import com.zt.baseapp.pt.widget.CarouselView.CarouselView;
import com.zt.baseapp.utils.ACache;
import com.zt.baseapp.utils.ACacheKey;
import com.zt.baseapp.utils.ScreenUtils;
import com.zt.baseapp.utils.UiUtil;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(AcListPresenter_pt.class)
public class CreateAcActivity_pt extends BaseActivity<AcInfoPresenter_pt> {
    public static CreateAcActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    Activity_pt activity_pt;

    TextView tv_name;
    TextView tv_price;
    TextView tv_oldPrice;
    TextView tv_soldNum;
    TextView tv_leftTime;
    TextView tv_shop;
    TextView tv_address;
    TextView tv_availableTime;
    TextView tv_tip;
    TextView tv_stop;
    TextView tv_abandon;
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
        tv_stop = (TextView) findViewById(R.id.tv_stop);
        tv_abandon = (TextView) findViewById(R.id.tv_abandon);

        cv.getLayoutParams().height = ScreenUtils.getScreenWidth();
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        activity_pt = (Activity_pt) getIntent().getSerializableExtra("ac");

        if (activity_pt != null) {
            setData();
        }

    }

    void setData(){
        tv_name.setText(activity_pt.name);
        tv_price.setText( "￥" +activity_pt.ptGood.price);
        tv_oldPrice.setText("原价￥" + activity_pt.ptGood.originalPrice);
        tv_soldNum.setText("已售" + activity_pt.saleNum);
        tv_leftTime.setText(activity_pt.endTime);
        tv_shop.setText(activity_pt.storeName);
        tv_address.setText(activity_pt.merchantAddress);
        tv_availableTime.setText("有效期：" + activity_pt.beginTime + " 至 " + activity_pt.endTime);
        tv_tip.setText(activity_pt.saleRemarks);

        if (activity_pt.ptGood != null || activity_pt.ptGood.imgUrl != null) {
            String[] imgs = activity_pt.ptGood.imgUrl.split(",");
            if (imgs.length != 0) {
                setCv(imgs);
            }
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
                        startActivity(new Intent(context, CreateAddGoodsActivity_pt.class).putExtra("imgUrl", activity_pt.ptGood.imgUrl));
                    }
                });
                return view;
            }

            @Override
            public int getCount() {
                return strArray.length;
            }
        });
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, CreateAddGoodsActivity_pt.class).putExtra("imgUrl", activity_pt.ptGood.imgUrl));
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
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.tv_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_pt != null) {
                    HttpMethods.start(HttpMethods.getInstance().demoService.changeAcStatus(token, activity_pt.id, 2), new Subscriber<Response>() {
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
                                Toast.makeText(context, "已停用", Toast.LENGTH_SHORT).show();
                                tv_stop.setClickable(false);
                                tv_stop.setText("已停用");
                            }
                        }
                    });
                }
            }
        });
        findViewById(R.id.tv_abandon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_pt != null) {
                    HttpMethods.start(HttpMethods.getInstance().demoService.changeAcStatus(token, activity_pt.id, 3), new Subscriber<Response>() {
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
                                Toast.makeText(context, "已作废", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
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
