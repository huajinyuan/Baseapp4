package com.example.choujiang.cj.ac_acSetting.lucky;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.choujiang.R;
import com.example.choujiang.utils.SizeUtils;
import com.example.choujiang.utils.UiUtil;

/**
 * Created by jeanboy on 2017/4/20.
 */

public class PanelItemView extends FrameLayout implements ItemView{

    private View overlay;
    private ImageView iv_award;

    public PanelItemView(Context context) {
        this(context, null);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_panel_item, this);
        overlay = findViewById(R.id.overlay);
        iv_award = (ImageView) findViewById(R.id.iv_award);
    }

    @Override
    public void setFocus(boolean isFocused) {
        if (overlay != null) {
            overlay.setVisibility(isFocused ? VISIBLE : INVISIBLE);
        }
    }

    @Override
    public void setImage(String url) {
        UiUtil.setRoundImage(iv_award, url, SizeUtils.dp2px(iv_award.getContext(), 10));
    }
}
