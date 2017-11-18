package com.example.zhongchou.zc.ac_ptbb;

import android.os.Bundle;

import com.example.zhongchou.di.BaseAppManager;
import com.example.zhongchou.module.base.BasePresenter;

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
