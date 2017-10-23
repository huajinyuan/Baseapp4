package com.example.huaxiang.thirdpart.share;


import com.example.huaxiang.thirdpart.bean.EnumPlatform;
import com.example.huaxiang.thirdpart.bean.ShareEntity;

import rx.subjects.PublishSubject;

public interface IAction {

    PublishSubject<Boolean> share(EnumPlatform.Method method, ShareEntity shareEntity);

}
