package com.example.zhongchou.thirdpart.share;

import com.example.zhongchou.thirdpart.bean.EnumPlatform;
import com.example.zhongchou.thirdpart.bean.ShareEntity;

import rx.subjects.PublishSubject;

public interface IAction {

    PublishSubject<Boolean> share(EnumPlatform.Method method, ShareEntity shareEntity);

}
