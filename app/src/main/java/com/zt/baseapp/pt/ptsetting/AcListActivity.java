package com.zt.baseapp.pt.ptsetting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.pt.adapter.AcListAdapter;



public class AcListActivity extends BaseActivity<AcListPresenter> implements AdapterView.OnItemClickListener {

//    private Unbinder unbinder;
//    @BindView(R.id.lv_content)
    ListView mLv;
//    @BindView(R.id.tv_topbar_title)
    TextView tvTitle;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ac_list);
//        unbinder = ButterKnife.bind(this);
//        mLv.setAdapter(new AcListAdapter(this));
//        mLv.setOnItemClickListener(this);
//        tvTitle.setText("活动列表");
//    }
//

//
//    @OnClick({R.id.img_topbar_back})
//    public void Onclick(View v) {
//        switch (v.getId()) {
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
        return R.layout.activity_ac_list;
    }

    @Override
    protected void initView() {

        mLv = findView(R.id.lv_content);
        tvTitle = findView(R.id.tv_topbar_title);
    }

    @Override
    protected void initData() {
        mLv.setAdapter(new AcListAdapter(this));
        mLv.setOnItemClickListener(this);
        tvTitle.setText("活动列表");
    }

    @Override
    protected void setListener() {
        findView(R.id.img_topbar_back).setOnClickListener(view -> finish());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, AcInfoActivity.class);
        startActivity(intent);
    }
}
