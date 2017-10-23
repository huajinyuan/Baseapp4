package com.example.huaxiang.rxpicture.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.huaxiang.rxpicture.bean.ImageItem;
import com.example.huaxiang.rxpicture.utils.RxPickerManager;

import java.util.List;

import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * @author Smile
 * @time 2017/4/18  下午6:38
 * @desc ${TODD}
 */
public class ResultHandlerFragment extends Fragment {

    PublishSubject<List<ImageItem>> resultSubject = PublishSubject.create();
    BehaviorSubject<Boolean> attachSubject = BehaviorSubject.create();

    public static final int REQUEST_CODE = 0x00100;

    public static ResultHandlerFragment newInstance() {
        return new ResultHandlerFragment();
    }

    public PublishSubject<List<ImageItem>> getResultSubject() {
        return resultSubject;
    }

    public BehaviorSubject<Boolean> getAttachSubject() {
        return attachSubject;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            resultSubject.onNext(RxPickerManager.getInstance().getResult(data));
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
}
