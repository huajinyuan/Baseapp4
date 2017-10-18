package com.zt.baseapp.module.listgroup.adapter;

import android.content.Context;

import com.zt.baseapp.module.listgroup.ItemViewDelegate;
import com.zt.baseapp.module.listgroup.utils.ViewHolder;

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
