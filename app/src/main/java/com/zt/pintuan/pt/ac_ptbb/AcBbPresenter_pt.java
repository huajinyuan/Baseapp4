package com.zt.pintuan.pt.ac_ptbb;

import android.os.Bundle;

import com.zt.pintuan.di.BaseAppManager;
import com.zt.pintuan.module.base.BasePresenter;

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
