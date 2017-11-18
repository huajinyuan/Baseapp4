package com.example.zhongchou.rxpicture.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhongchou.rxpicture.bean.ImageItem;
import com.example.zhongchou.rxpicture.utils.DensityUtil;
import com.example.zhongchou.rxpicture.utils.RxPickerManager;

import java.util.List;

import uk.co.senab.photoview.PhotoView;


public class PreviewAdapter extends PagerAdapter {

    private List<ImageItem> data;

    public PreviewAdapter(List<ImageItem> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        photoView.setLayoutParams(layoutParams);
        ImageItem imageItem = data.get(position);
        container.addView(photoView);
        int deviceWidth = DensityUtil.getDeviceWidth(container.getContext());
        RxPickerManager.getInstance().display(photoView, imageItem.getPath(), deviceWidth, deviceWidth);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
