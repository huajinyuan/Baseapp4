package com.zt.baseapp.pt.ptbb;

import android.os.Bundle;

import com.zt.baseapp.di.BaseAppManager;
import com.zt.baseapp.module.base.BasePresenter;
import com.zt.baseapp.pt.PtIndextActivity;

/**
 * Created by caiyk on 2017/9/28.
 */

public class BbActivityPresenter extends BasePresenter<BbActivityActivity> {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        BaseAppManager.getInstance().inject(this);
    }

}
