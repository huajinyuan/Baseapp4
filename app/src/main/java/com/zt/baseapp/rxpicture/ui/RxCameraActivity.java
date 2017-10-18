package com.zt.baseapp.rxpicture.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.zt.baseapp.R;
import com.zt.baseapp.rxpicture.bean.ImageItem;
import com.zt.baseapp.rxpicture.utils.CameraHelper;
import com.zt.baseapp.rxpicture.utils.T;

import java.util.ArrayList;
import java.util.Arrays;

import static com.zt.baseapp.rxpicture.ui.PickerFragment.MEDIA_RESULT;

/**
 * Created by caiyk on 2017/9/22.
 */

public class RxCameraActivity extends AppCompatActivity {
    private static final int READ_STORAGE_PERMISSION = 0;
    public static final int CAMERA_REQUEST = 0x001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    @TargetApi(16)
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION);
        } else {
            takePictures();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePictures();
            } else {
                T.show(this, getString(R.string.permissions_error));
                finish();
            }
        }
    }

    private void takePictures() {
        CameraHelper.take(this, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //take camera
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST) {
            ImageItem imageItem = new ImageItem();
            imageItem.setPath(CameraHelper.getTakeImageFile().getAbsolutePath());
            handleResult(new ArrayList<>(Arrays.asList(imageItem)));
        }
        finish();
    }

    private void handleResult(ArrayList<ImageItem> data) {
        Intent intent = new Intent();
        intent.putExtra(MEDIA_RESULT, data);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
