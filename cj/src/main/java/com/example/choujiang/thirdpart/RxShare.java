package com.example.choujiang.thirdpart;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;

import com.example.choujiang.model.Response;
import com.example.choujiang.thirdpart.bean.EnumPlatform;
import com.example.choujiang.thirdpart.bean.RxShareManager;
import com.example.choujiang.thirdpart.bean.ShareEntity;
import com.example.choujiang.thirdpart.share.ShareResultFragment;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by caiyk on 2017/9/26.
 */

public class RxShare {

    public static RxShare of() {
        return new RxShare();
    }

    public RxShare method(EnumPlatform.Method method) {
        RxShareManager.getInstance().setMethod(method);
        return this;
    }

    public RxShare share(ShareEntity shareEntity) {
        RxShareManager.getInstance().setShareEntity(shareEntity);
        return this;
    }

    public Observable<Response> start(Activity activity) {
        return start(activity.getFragmentManager());
    }

    public Observable<Response> start(Fragment fragment) {
        return start(fragment.getFragmentManager());
    }

    private Observable<Response> start(FragmentManager fragmentManager) {
        ShareResultFragment fragment = (ShareResultFragment) fragmentManager.findFragmentByTag(ShareResultFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = ShareResultFragment.newInstance();
            fragmentManager.beginTransaction().add(fragment, fragment.getClass().getSimpleName()).commit();
        } else if (fragment.isDetached()) {
            fragmentManager.beginTransaction().attach(fragment).commit();
        }
        return getResponse(fragment);
    }

    private Observable<Response> getResponse(ShareResultFragment fragment) {
        return fragment.getAttachSubject().filter(aBoolean -> aBoolean)
                .flatMap(aBoolean -> {
                    fragment.share();
                    return fragment.getResultSubject();
                }).take(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
