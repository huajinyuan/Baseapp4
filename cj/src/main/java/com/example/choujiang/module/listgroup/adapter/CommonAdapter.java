package com.example.choujiang.module.listgroup.adapter;

import android.content.Context;


import com.example.choujiang.module.listgroup.ItemViewDelegate;
import com.example.choujiang.module.listgroup.utils.ViewHolder;

import java.util.List;

/**
 * Created by caiyk on 2016/12/30 18:51
 * Email:781208202@qq.com
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);


}
