package com.zt.baseapp.pt.ptsetting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.module.base.BaseActivity;



public class AcInfoActivity extends BaseActivity<AcInfoPresenter> {
//    private Unbinder unbinder;
//    @BindView(R.id.tv_topbar_title)
    TextView tvTitle;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ac_info);
////        unbinder = ButterKnife.bind(this);
//        tvTitle.setText("活动详情");
//    }

    //    @OnClick({R.id.img_topbar_back})
//    public void Onclick(View v){
//        switch (v.getId()){
//            case R.id.img_topbar_back:
//                this.finish();
//                break;
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbinder.unbind();
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ac_info;
    }

    @Override
    protected void initView() {

        tvTitle = findView(R.id.tv_topbar_title);
    }

    @Override
    protected void initData() {
        tvTitle.setText("活动详情");
    }

    @Override
    protected void setListener() {
        findView(R.id.img_topbar_back).setOnClickListener(view -> finish());
    }
}
