package com.zt.baseapp.rxpicture.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.zt.baseapp.R;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.rxpicture.utils.T;

/**
 * Created by caiyk on 2017/8/31.
 */
public class RxPickerActivity extends BaseActivity {

    private static final int READ_STORAGE_PERMISSION = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.rxpicker_activity_picker;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        requestPermission();
    }

    @Override
    protected void setListener() {

    }

    @TargetApi(16)
    private void requestPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(RxPickerActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION);
//        } else {
            setupFragment();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupFragment();
            } else {
                T.show(RxPickerActivity.this, getString(R.string.permissions_error));
                finish();
            }
        }
    }

    private void setupFragment() {
//        String tag = PickerFragment.class.getSimpleName();
        PickerFragment fragment =null;
//        PickerFragment fragment = (PickerFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = PickerFragment.newInstance();
        }
//        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment, tag).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commitAllowingStateLoss();
    }
}
