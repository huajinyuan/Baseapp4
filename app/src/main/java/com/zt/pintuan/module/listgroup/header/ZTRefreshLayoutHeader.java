package com.zt.pintuan.module.listgroup.header;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.zt.pintuan.R;
import com.zt.pintuan.utils.SizeUtils;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by caiyk on 2017/9/9.
 */

public class ZTRefreshLayoutHeader extends PtrFrameLayout {
    public ZTRefreshLayoutHeader(Context context) {
        super(context);
    }

    public ZTRefreshLayoutHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public ZTRefreshLayoutHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZTRefreshLayoutHeader);
        int letterColor = typedArray.getColor(R.styleable.ZTRefreshLayoutHeader_loading_color,
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(getContext());
        storeHouseHeader.setPadding(0, SizeUtils.dp2px(getContext(), 15), 0, 0);
        storeHouseHeader.setTextColor(letterColor);
        storeHouseHeader.initWithString("zhongtu");
        this.setHeaderView(storeHouseHeader);
        this.addPtrUIHandler(storeHouseHeader);
        this.setDurationToClose(200);
        this.setDurationToCloseHeader(500);
        this.setKeepHeaderWhenRefresh(true);
        this.setPullToRefresh(true);
        this.setRatioOfHeaderHeightToRefresh(1.2f);
        this.setResistance(1.7f);
    }

}
