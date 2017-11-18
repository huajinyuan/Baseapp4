package com.example.zhongchou.module.listgroup;


import com.example.zhongchou.module.listgroup.utils.ViewHolder;

/**
 * Created by caiyk on 2016/12/30 18:49
 * Email:781208202@qq.com
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
