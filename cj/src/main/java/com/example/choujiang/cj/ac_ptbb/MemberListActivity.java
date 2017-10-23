package com.example.choujiang.cj.ac_ptbb;

import android.widget.ListView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.adapter.MemberlistAdapter;
import com.example.choujiang.module.base.BaseActivity;


public class MemberListActivity extends BaseActivity<MemberListPresenter> {

//    private Unbinder unbinder;
//    @BindView(R.id.lv_content)
    ListView mLv;
//    @BindView(R.id.tv_topbar_title)
    TextView tvTitle;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_member_list);
//        unbinder = ButterKnife.bind(this);
//        mLv.setAdapter(new MemberlistAdapter(this));
//        tvTitle.setText("员工排行");
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
        return R.layout.activity_member_list;
    }

    @Override
    protected void initView() {
        mLv = findView(R.id.lv_content);
        tvTitle = findView(R.id.tv_topbar_title);

    }

    @Override
    protected void initData() {
        tvTitle.setText("员工排行");
        mLv.setAdapter(new MemberlistAdapter(this));
    }

    @Override
    protected void setListener() {
        findView(R.id.img_topbar_back).setOnClickListener(view -> finish());

    }

}
