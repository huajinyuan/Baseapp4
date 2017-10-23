package com.example.choujiang.rxpicture.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.module.titlebar.SimpleTitleBar;
import com.example.choujiang.module.titlebar.TitleBarBuilder;
import com.example.choujiang.rxpicture.bean.ImageItem;
import com.example.choujiang.rxpicture.ui.adapter.PreviewAdapter;

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
