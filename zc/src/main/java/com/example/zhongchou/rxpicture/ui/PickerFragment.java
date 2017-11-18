package com.example.zhongchou.rxpicture.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhongchou.R;
import com.example.zhongchou.module.base.BaseFragment;
import com.example.zhongchou.rxpicture.bean.FolderClickEvent;
import com.example.zhongchou.rxpicture.bean.ImageFolder;
import com.example.zhongchou.rxpicture.bean.ImageItem;
import com.example.zhongchou.rxpicture.ui.adapter.PickerFragmentAdapter;
import com.example.zhongchou.rxpicture.utils.CameraHelper;
import com.example.zhongchou.rxpicture.utils.DensityUtil;
import com.example.zhongchou.rxpicture.utils.PickerConfig;
import com.example.zhongchou.rxpicture.utils.RxPickerManager;
import com.example.zhongchou.rxpicture.utils.T;
import com.example.zhongchou.rxpicture.widget.DividerGridItemDecoration;
import com.example.zhongchou.rxpicture.widget.PopWindowManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by caiyk on 2017/8/31.
 */
@RequiresPresenter(PickerPresenter.class)
public class PickerFragment extends BaseFragment<PickerPresenter> {

    public static final int DEFAULT_SPAN_COUNT = 3;
    public static final int CAMERA_REQUEST = 0x001;
    private static final int CAMERA_PERMISSION = 0x002;

    public static final String MEDIA_RESULT = "MEDIA_RESULT";

    private TextView title;
    private RecyclerView recyclerView;
    private Button btnSelectPreview;
    private Button btnSure;
//    private RelativeLayout rlBottom;
    private PickerFragmentAdapter adapter;
    private List<ImageFolder> allFolder;
    private PickerConfig config;

    public static PickerFragment newInstance() {
        return new PickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rxpicker_fragment_picker;
    }


    @Override
    protected void initView() {
        config = RxPickerManager.getInstance().getConfig();
        recyclerView = findView(R.id.recyclerView);
        title = findView(R.id.title);
        btnSelectPreview = findView(R.id.btnSelectPreview);
        btnSure = findView(R.id.btnSure);
//        rlBottom = findView(R.id.rlBottom);
//        rlBottom.setVisibility(config.isSingle() ? View.GONE : View.VISIBLE);
//        rlBottom.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), DEFAULT_SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);

        final DividerGridItemDecoration decoration = new DividerGridItemDecoration(getActivity());
        Drawable divider = decoration.getDivider();
        int imageWidth = DensityUtil.getDeviceWidth(getActivity()) / DEFAULT_SPAN_COUNT + divider.getIntrinsicWidth() * DEFAULT_SPAN_COUNT - 1;

        adapter = new PickerFragmentAdapter(imageWidth);
        adapter.setCameraClickListener(new CameraClickListener());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                btnSure.setText(getString(R.string.select_confim, adapter.getCheckImage().size()));
            }
        });

        btnSure.setText(getString(R.string.select_confim, adapter.getCheckImage().size()));
        getPresenter().loadAllImage();
    }

    @Override
    protected void setListener() {
        ClickView(R.id.ivLeft).subscribe(aVoid -> getActivity().finish());
        ClickView(btnSure).subscribe(aVoid -> {
            int minValue = config.getMinValue();
            ArrayList<ImageItem> checkImage = adapter.getCheckImage();
            if (checkImage.size() < minValue) {
                T.show(getActivity(), getString(R.string.min_image, minValue));
                return;
            }
            handleResult(checkImage);
        });

        ClickView(btnSelectPreview).subscribe(aVoid -> {
            ArrayList<ImageItem> checkImage = adapter.getCheckImage();
            if (checkImage.isEmpty()) {
                T.show(getActivity(), getString(R.string.select_one_image));
                return;
            }
            PreviewActivity.start(getActivity(), checkImage);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void folderClick(FolderClickEvent folderClickEvent) {
        String folderName = folderClickEvent.getFolder().getName();
        title.setText(folderName);
        refreshData(allFolder.get(folderClickEvent.getPosition()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void imageItemSelect(ImageItem imageItem) {
        ArrayList<ImageItem> data = new ArrayList<>();
        data.add(imageItem);
        handleResult(data);
    }

    private void refreshData(ImageFolder folder) {
        adapter.setData(folder.getImages());
        adapter.notifyDataSetChanged();
    }

    private void initPopWindow(List<ImageFolder> data) {
        new PopWindowManager().init(title, data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //take camera
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST) {
            handleCameraResult();
        }
    }

    private void handleCameraResult() {
        File file = CameraHelper.getTakeImageFile();
        CameraHelper.scanPic(getActivity(), file);
        for (ImageFolder imageFolder : allFolder) {
            imageFolder.setChecked(false);
        }
        ImageFolder allImageFolder = allFolder.get(0);
        allImageFolder.setChecked(true);
        ImageItem item = new ImageItem(0, file.getAbsolutePath(), file.getName(), System.currentTimeMillis());
        allImageFolder.getImages().add(0, item);
        EventBus.getDefault().post(new FolderClickEvent(0, allImageFolder));
    }

    private void handleResult(ArrayList<ImageItem> data) {
        Intent intent = new Intent();
        intent.putExtra(MEDIA_RESULT, data);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    public void showAllImage(List<ImageFolder> datas) {
        allFolder = datas;
        adapter.setData(datas.get(0).getImages());
        adapter.notifyDataSetChanged();
        initPopWindow(datas);
    }

    @TargetApi(23)
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
        } else {
            takePictures();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePictures();
            } else {
                T.show(getActivity(), getString(R.string.permissions_error));
            }
        }
    }

    private void takePictures() {
        CameraHelper.take(PickerFragment.this, CAMERA_REQUEST);
    }

    private class CameraClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermission();
            } else {
                takePictures();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
