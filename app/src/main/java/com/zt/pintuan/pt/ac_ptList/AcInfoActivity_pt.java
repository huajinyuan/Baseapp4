package com.zt.pintuan.pt.ac_ptList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zt.pintuan.R;
import com.zt.pintuan.model.Response;
import com.zt.pintuan.module.base.BaseActivity;
import com.zt.pintuan.network.retrofit.HttpMethods;
import com.zt.pintuan.pt.ac_ptList.ac_createAc.CreateAcActivity_pt;
import com.zt.pintuan.pt.ac_staffSend.m.Activity_pt;
import com.zt.pintuan.pt.utils.DisplayMetricsUtil;
import com.zt.pintuan.pt.widget.CarouselView.CarouselView;
import com.zt.pintuan.utils.ACache;
import com.zt.pintuan.utils.ACacheKey;
import com.zt.pintuan.utils.UiUtil;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(AcInfoPresenter_pt.class)
public class AcInfoActivity_pt extends BaseActivity<AcInfoPresenter_pt> {
    public static AcInfoActivity_pt instance;
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    Activity_pt activity_pt;
    String actId;

    TextView tv_name;
    TextView tv_price;
    TextView tv_oldPrice;
    TextView tv_soldNum;
    TextView tv_leftTime;
    TextView tv_shop;
    TextView tv_address;
    TextView tv_availableTime;
    TextView tv_tip;
    TextView bt_stop;
    TextView bt_abandon;
    CarouselView cv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ac_info_pt;
    }

    @Override
    protected void initView() {
        instance = this;
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("活动详情");
        tv_topbar_right.setVisibility(View.VISIBLE);
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
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_abandon = (Button) findViewById(R.id.bt_abandon);
        cv = (CarouselView) findViewById(R.id.cv);

        cv.getLayoutParams().height = DisplayMetricsUtil.getScreenWidth(context);
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        actId = getIntent().getStringExtra("actId");

        if (actId != null) {
            getData();
        }

    }

    void setData() {
        //检查活动状态
        if (activity_pt.status == 2) {
            bt_stop.setClickable(false);
            bt_stop.setText("已停用");
        } else if (activity_pt.status == 3) {
            bt_stop.setClickable(false);
            bt_abandon.setClickable(false);
            bt_abandon.setText("已作废");
        }

        tv_name.setText(activity_pt.name);
        tv_price.setText(activity_pt.ptGood.price + "");
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
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("aaa", "click view");
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
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_pt != null) {
                    startActivity(new Intent(context, CreateAcActivity_pt.class).putExtra("ac", activity_pt));
                }
            }
        });
        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_pt != null) {
                    stopAc();
                }
            }
        });
        bt_abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_pt != null) {
                    abandonAc();
                }
            }
        });
    }

    public void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.getAcDetail(token, actId), new Subscriber<Response<Activity_pt>>() {
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
            public void onNext(Response<Activity_pt> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    activity_pt = arrayListResponse.data;
                    setData();
                }
            }
        });
    }
    void stopAc(){
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
                    bt_stop.setClickable(false);
                    bt_stop.setText("已停用");
                }
            }
        });
    }

    void abandonAc(){
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
                    bt_stop.setClickable(false);
                    bt_abandon.setClickable(false);
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
