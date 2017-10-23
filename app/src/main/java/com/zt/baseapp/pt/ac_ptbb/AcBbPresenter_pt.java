package com.zt.baseapp.pt.ac_ptbb;

import android.os.Bundle;

import com.zt.baseapp.di.BaseAppManager;
import com.zt.baseapp.module.base.BasePresenter;

/**
 * Created by caiyk on 2017/9/28.
 */

public class AcBbPresenter_pt extends BasePresenter<AcBbActivity_pt> {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        BaseAppManager.getInstance().inject(this);
    }

}
