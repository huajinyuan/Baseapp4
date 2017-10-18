package com.zt.baseapp.pt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zt.baseapp.R;
import com.zt.baseapp.di.BaseAppManager;
import com.zt.baseapp.di.EnumFile;
import com.zt.baseapp.module.base.BasePresenter;
import com.zt.baseapp.pt.memberget.MemberGetActivity;
import com.zt.baseapp.pt.ptbb.BbActivityActivity;
import com.zt.baseapp.pt.ptbb.MemberListActivity;
import com.zt.baseapp.pt.ptbb.PdListActivity;
import com.zt.baseapp.pt.ptsetting.AcListActivity;
import com.zt.baseapp.rxpicture.ui.RxCropActivity;
import com.zt.baseapp.utils.TimeUtils;

import java.io.File;


/**
 * Created by caiyk on 2017/9/28.
 */

public class PtIndextPresenter extends BasePresenter<PtIndextActivity> {
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
                intent = new Intent(mContext, PdListActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_action_memberlist:
                intent = new Intent(mContext, MemberListActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_action_pt_ac:
                intent = new Intent(mContext, AcListActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_action_memberget:
                intent = new Intent(mContext, MemberGetActivity.class);
                mContext.startActivity(intent);
                break;

        }


    }*/
}
