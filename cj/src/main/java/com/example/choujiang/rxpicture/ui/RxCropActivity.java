package com.example.choujiang.rxpicture.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import com.example.choujiang.R;
import com.example.choujiang.base.ReturnCode;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.module.titlebar.SimpleTitleBar;
import com.example.choujiang.module.titlebar.TitleBarBuilder;
import com.example.choujiang.network.exception.ErrorThrowable;
import com.example.choujiang.rxpicture.bean.ImageItem;
import com.example.choujiang.rxpicture.utils.CropConfig;
import com.example.choujiang.rxpicture.utils.RxCropManager;
import com.example.choujiang.rxpicture.widget.cropview.CropImageView;
import com.example.choujiang.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;

import nucleus.factory.RequiresPresenter;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.choujiang.rxpicture.ui.PickerFragment.MEDIA_RESULT;


/**
 * Created by caiyk on 2017/9/23.
 */
@RequiresPresenter(RxCropPresenter.class)
public class RxCropActivity extends BaseActivity<RxCropPresenter> {
    private CropImageView cropImageView;
    private CropConfig mCropConfig;
    private Subscription mSubscription;


    @Override
    protected void initTitleBar(TitleBarBuilder titleBarBuild) {
        titleBarBuild.config(SimpleTitleBar.class).setTitle("裁剪")
                .setRightText("确定").setOnRightClickListener(view -> cropImage());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rxpicker_activity_crop;
    }

    @Override
    protected void initView() {
        cropImageView = findView(R.id.cropImageView);

    }

    @Override
    protected void initData() {
        mCropConfig = RxCropManager.getInstance().getCropConfig();
        if (mCropConfig == null || mCropConfig.getUri() == null) finish();
        cropImageView.setCropMode(mCropConfig.getCropMode());
        cropImageView.setGuideShowMode(mCropConfig.getShowMode());
        cropImageView.setHandleShowMode(mCropConfig.getShowMode());
        cropImageView.load(mCropConfig.getUri()).useThumbnail(true).executeAsCompletable()
                .subscribe(() -> {
                }, throwable -> showError(throwable));

    }

    private Subscription cropImage() {
        return mSubscription = cropImageView.crop(mCropConfig.getUri()).executeAsSingle()
                .flatMap(bitmap -> {
                    String filePath = getPresenter().getFilePath();
                    boolean isSuccess = ImageUtils.save(bitmap, filePath, Bitmap.CompressFormat.JPEG);
                    return isSuccess ? Single.just(filePath) : Single.error(new ErrorThrowable(ReturnCode.CODE_ERROR, ""));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> showLoadingDialog())
                .doOnSuccess(uri -> closeLoadingDialog())
                .subscribe(filePath -> {
                    ImageItem imageItem = new ImageItem();
                    imageItem.setPath(filePath);
                    handleResult(new ArrayList<>(Arrays.asList(imageItem)));
                }, throwable -> showError(throwable));
    }

    private void handleResult(ArrayList<ImageItem> data) {
        Intent intent = new Intent();
        intent.putExtra(MEDIA_RESULT, data);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
