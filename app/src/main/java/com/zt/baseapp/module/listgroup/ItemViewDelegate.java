package com.zt.baseapp.module.listgroup;


import com.zt.baseapp.module.listgroup.utils.ViewHolder;

/**
 * Created by caiyk on 2016/12/30 18:49
 * Email:781208202@qq.com
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
