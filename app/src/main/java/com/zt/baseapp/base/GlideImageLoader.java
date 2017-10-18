package com.zt.baseapp.base;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zt.baseapp.R;
import com.zt.baseapp.rxpicture.utils.RxPickerImageLoader;


public class GlideImageLoader implements RxPickerImageLoader {

    @Override
    public void display(ImageView imageView, String path, int width, int height) {
        Glide.with(imageView.getContext()).load(path).error(R.drawable.defaut_image).centerCrop().override(width, height).into(imageView);
    }
}
