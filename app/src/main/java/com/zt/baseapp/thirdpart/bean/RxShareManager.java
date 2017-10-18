package com.zt.baseapp.thirdpart.bean;

/**
 * Created by caiyk on 2017/9/26.
 */

public class RxShareManager {
    private EnumPlatform.Method mMethod;
    private ShareEntity mShareEntity;

    private static final class RxShareManagerHolder {
        private static final RxShareManager INSTANCE = new RxShareManager();
    }

    public static final RxShareManager getInstance() {
        return RxShareManagerHolder.INSTANCE;
    }

    public EnumPlatform.Method getMethod() {
        return mMethod;
    }

    public void setMethod(EnumPlatform.Method method) {
        mMethod = method;
    }

    public ShareEntity getShareEntity() {
        return mShareEntity;
    }

    public void setShareEntity(ShareEntity shareEntity) {
        mShareEntity = shareEntity;
    }
}
