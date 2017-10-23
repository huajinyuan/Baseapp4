package com.example.choujiang.thirdpart.share;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.choujiang.model.Response;
import com.example.choujiang.thirdpart.bean.RxShareManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by caiyk on 2017/9/26.
 */

public class ShareResultFragment extends Fragment {
    PublishSubject<Response> resultSubject = PublishSubject.create();
    BehaviorSubject<Boolean> attachSubject = BehaviorSubject.create();

    public static final int REQUEST_CODE = 0x00100;

    public static ShareResultFragment newInstance() {
        return new ShareResultFragment();
    }

    public PublishSubject<Response> getResultSubject() {
        return resultSubject;
    }

    public BehaviorSubject<Boolean> getAttachSubject() {
        return attachSubject;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    public void share() {
        IAction action = null;
        switch (RxShareManager.getInstance().getMethod()) {
            case SHARE_WX_FRIEND:
            case SHARE_WX_TIMELINE:
                action = new WXManager(getActivity());
                break;
        }
        if (action != null)
            action.share(RxShareManager.getInstance().getMethod(), RxShareManager.getInstance().getShareEntity());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(Response response) {
        resultSubject.onNext(response);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachSubject.onNext(true);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23) {
            attachSubject.onNext(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
