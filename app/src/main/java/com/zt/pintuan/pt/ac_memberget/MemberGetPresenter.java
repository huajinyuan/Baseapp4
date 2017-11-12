package com.zt.pintuan.pt.ac_memberget;

import android.os.Bundle;

import com.zt.pintuan.di.BaseAppManager;
import com.zt.pintuan.module.base.BasePresenter;

/**
 * Created by caiyk on 2017/9/28.
 */

public class MemberGetPresenter extends BasePresenter<MemberGetActivity> {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        BaseAppManager.getInstance().inject(this);
    }

}
