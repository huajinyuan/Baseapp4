package com.zt.baseapp.rxpicture.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.module.titlebar.SimpleTitleBar;
import com.zt.baseapp.module.titlebar.TitleBarBuilder;
import com.zt.baseapp.rxpicture.bean.ImageItem;
import com.zt.baseapp.rxpicture.ui.adapter.PreviewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by caiyk on 2017/8/31.
 */
public class PreviewActivity extends BaseActivity {

    private ViewPager vpPreview;
    private PreviewAdapter vpAdapter;
    private List<ImageItem> data;

    public static void start(Context context, ArrayList<ImageItem> data) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra("preview_list", data);
        context.startActivity(intent);
    }

    @Override
    public void getExtra() {
        super.getExtra();
        data = (List<ImageItem>) getIntent().getSerializableExtra("preview_list");
    }

    @Override
    protected void initTitleBar(TitleBarBuilder titleBarBuild) {
        super.initTitleBar(titleBarBuild);
        titleBarBuild.config(SimpleTitleBar.class).setTitle("1/" + data.size());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rxpicker_activity_preview;
    }

    @Override
    protected void initView() {
        vpPreview = (ViewPager) findViewById(R.id.vpPreview);
    }

    @Override
    protected void initData() {
        vpAdapter = new PreviewAdapter(data);
        vpPreview.setAdapter(vpAdapter);
    }

    @Override
    protected void setListener() {
        vpPreview.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ((TextView) getBaseTitleBar().getView(R.id.tvTitle)).setText(position + 1 + "/" + data.size());
            }
        });
    }
}
