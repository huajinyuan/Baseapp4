package com.example.choujiang.cj.ac_memberget;

import android.os.Bundle;

import com.example.choujiang.di.BaseAppManager;
import com.example.choujiang.module.base.BasePresenter;


/**
 * Created by caiyk on 2017/9/28.
 */

public class MemberGetListPresenter extends BasePresenter<MemberGetListActivity> {
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
                intent = new Intent(mContext, BbActivityActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_action_ptlist:
                intent = new Intent(mContext, CjHistoryActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_action_memberlist:
                intent = new Intent(mContext, MemberListActivity.class);
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
