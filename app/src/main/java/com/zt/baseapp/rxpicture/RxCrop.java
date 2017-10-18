package com.zt.baseapp.rxpicture;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;

import com.zt.baseapp.rxpicture.bean.ImageItem;
import com.zt.baseapp.rxpicture.ui.ResultHandlerFragment;
import com.zt.baseapp.rxpicture.ui.RxCropActivity;
import com.zt.baseapp.rxpicture.utils.RxCropManager;

import rx.Observable;

/**
 * Created by caiyk on 2017/9/23.
 */

public class RxCrop {

    public RxCrop() {
    }

    public static RxCrop of() {
        return new RxCrop();
    }

    public RxCrop crop(Uri uri) {
        RxCropManager.getInstance().setUril(uri);
        return this;
    }

    public Observable<ImageItem> start(Activity activity) {
        return start(activity.getFragmentManager());
    }

    public Observable<ImageItem> start(Fragment fragment) {
        return start(fragment.getFragmentManager());
    }

    private Observable<ImageItem> start(FragmentManager fragmentManager) {
        ResultHandlerFragment fragment = (ResultHandlerFragment) fragmentManager.findFragmentByTag(ResultHandlerFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = ResultHandlerFragment.newInstance();
            fragmentManager.beginTransaction().add(fragment, fragment.getClass().getSimpleName()).commit();
        } else if (fragment.isDetached()) {
            fragmentManager.beginTransaction().attach(fragment).commit();
        }
        return getImageItem(fragment);
    }

    private Observable<ImageItem> getImageItem(ResultHandlerFragment finalFragment) {
        return finalFragment.getAttachSubject().filter(aBoolean -> aBoolean)
                .flatMap(aBoolean -> {
                    Intent intent = new Intent(finalFragment.getActivity(), RxCropActivity.class);
                    finalFragment.startActivityForResult(intent, ResultHandlerFragment.REQUEST_CODE);
                    return finalFragment.getResultSubject().map(imageItems -> imageItems.get(0));
                }).take(1);
    }
}
