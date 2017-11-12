package com.zt.pintuan.thirdpart.share;

import com.zt.pintuan.thirdpart.bean.EnumPlatform;
import com.zt.pintuan.thirdpart.bean.ShareEntity;

import rx.subjects.PublishSubject;

public interface IAction {

    PublishSubject<Boolean> share(EnumPlatform.Method method, ShareEntity shareEntity);

}
