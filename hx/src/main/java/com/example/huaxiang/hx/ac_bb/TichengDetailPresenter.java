package com.example.huaxiang.hx.ac_bb;

import android.os.Bundle;

import com.example.huaxiang.di.BaseAppManager;
import com.example.huaxiang.module.base.BasePresenter;

/**
 * Created by caiyk on 2017/9/28.
 */

public class TichengDetailPresenter extends BasePresenter<TichengDetailActivity> {
    //    @Inject
//    @Named(EnumFile.CACHE)
//    public File mCacheFile;
//    private Unbinder unbinder;
//    Context mContext;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        BaseAppManager.getInstance().inject(this);
//        unbinder = ButterKnife.bind(getView());
//        mContext = getView().getBaseContext();
    }

//    public String getFilePath() {
//        String fileName = "scv" + TimeUtils.getNowTimeString("yyyyMMdd_HHmmss") + ".jpeg";
//        return new File(mCacheFile, fileName).getAbsolutePath();
//    }


   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.img_topbar_back, R.id.tv_action_aclist, R.id.tv_action_ptlist, R.id.tv_action_memberlist, R.id.tv_action_pt_ac, R.id.tv_action_memberget})
    public void Onclick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_topbar_back:
                getView().finish();
                break;
            case R.id.tv_action_aclist:
                intent = new Intent(mContext, AcBbActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_action_ptlist:
                intent = new Intent(mContext, PtListActivity_pt.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_action_memberlist:
                intent = new Intent(mContext, StaffRankingActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_action_pt_ac:
                intent = new Intent(mContext, AwardListActivity_cj.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_action_memberget:
                intent = new Intent(mContext, MemberGetActivity.class);
                mContext.startActivity(intent);
                break;

        }


    }*/
}
