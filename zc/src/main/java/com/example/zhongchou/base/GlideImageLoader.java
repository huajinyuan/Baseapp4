package com.example.zhongchou.base;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zhongchou.R;
import com.example.zhongchou.rxpicture.utils.RxPickerImageLoader;


public class GlideImageLoader implements RxPickerImageLoader {

    @Override
    public void display(ImageView imageView, String path, int width, int height) {
        Glide.with(imageView.getContext()).load(path).error(R.drawable.defaut_image).centerCrop().override(width, height).into(imageView);
    }
}
