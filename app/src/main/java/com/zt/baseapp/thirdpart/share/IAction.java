package com.zt.baseapp.thirdpart.share;

import com.zt.baseapp.thirdpart.bean.EnumPlatform;
import com.zt.baseapp.thirdpart.bean.ShareEntity;

import rx.subjects.PublishSubject;

public interface IAction {

    PublishSubject<Boolean> share(EnumPlatform.Method method, ShareEntity shareEntity);

}
