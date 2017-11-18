package com.example.zhongchou.rxpicture.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.zhongchou.R;
import com.example.zhongchou.module.base.BasePresenter;
import com.example.zhongchou.rxpicture.bean.ImageFolder;
import com.example.zhongchou.rxpicture.bean.ImageItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by caiyk on 2017/8/31.
 */
public class PickerPresenter extends BasePresenter<PickerFragment> {
    private int REQUEST_ALL_fOLDER = 0X1;
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media._ID, // image id.
            MediaStore.Images.Media.DATA, // image absolute path.
            MediaStore.Images.Media.DISPLAY_NAME, // image name.
            MediaStore.Images.Media.DATE_ADDED, // The time to be added to the library.
            MediaStore.Images.Media.BUCKET_ID, // folder id.
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME // folder name.
    };

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableFirstPro(REQUEST_ALL_fOLDER, () -> loadAllFolder(),
                (pickerFragment, imageFolders) -> pickerFragment.showAllImage(imageFolders));
    }

    public void start() {

    }

    /**
     * Scan the list of pictures in the library.
     */
    private Observable<List<ImageFolder>> loadAllFolder() {
        return Observable.create(subscriber -> {
            Cursor cursor = MediaStore.Images.Media.query(getView().getContext().getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES);
            Map<String, ImageFolder> albumFolderMap = new HashMap<>();

            ImageFolder allImageImageFolder = new ImageFolder();
            allImageImageFolder.setChecked(true);
            allImageImageFolder.setName(getView().getContext().getString(R.string.all_phone_album));
            while (cursor.moveToNext()) {
                int imageId = cursor.getInt(0);
                String imagePath = cursor.getString(1);
                String imageName = cursor.getString(2);
                long addTime = cursor.getLong(3);

                int bucketId = cursor.getInt(4);
                String bucketName = cursor.getString(5);

                ImageItem ImageItem = new ImageItem(imageId, imagePath, imageName, addTime);
                allImageImageFolder.addPhoto(ImageItem);

                ImageFolder imageFolder = albumFolderMap.get(bucketName);
                if (imageFolder != null) {
                    imageFolder.addPhoto(ImageItem);
                } else {
                    imageFolder = new ImageFolder(bucketId, bucketName);
                    imageFolder.addPhoto(ImageItem);

                    albumFolderMap.put(bucketName, imageFolder);
                }
            }

            cursor.close();
            List<ImageFolder> imageFolders = new ArrayList<>();

            Collections.sort(allImageImageFolder.getImages());
            imageFolders.add(allImageImageFolder);

            for (Map.Entry<String, ImageFolder> folderEntry : albumFolderMap.entrySet()) {
                ImageFolder imageFolder = folderEntry.getValue();
                Collections.sort(imageFolder.getImages());
                imageFolders.add(imageFolder);
            }
            subscriber.onNext(imageFolders);
        });
    }

    public void loadAllImage() {
        start(REQUEST_ALL_fOLDER);
    }
}
