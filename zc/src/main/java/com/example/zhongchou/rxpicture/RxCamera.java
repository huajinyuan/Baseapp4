package com.example.zhongchou.rxpicture;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;

import com.example.zhongchou.rxpicture.bean.ImageItem;
import com.example.zhongchou.rxpicture.ui.ResultHandlerFragment;
import com.example.zhongchou.rxpicture.ui.RxCameraActivity;

import rx.Observable;

/**
 * Created by caiyk on 2017/9/22.
 */

public class RxCamera {

    public static RxCamera of() {
        return new RxCamera();
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
        return finalFragment.getAttachSubject().filter(aBoolean -> aBoolean).
                flatMap(aBoolean -> {
                    Intent intent = new Intent(finalFragment.getActivity(), RxCameraActivity.class);
                    finalFragment.startActivityForResult(intent, ResultHandlerFragment.REQUEST_CODE);
                    return finalFragment.getResultSubject().map(imageItems -> imageItems.get(0));
                }).take(1);
    }
}
