package com.example.zhongchou.rxpicture.widget;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zhongchou.R;
import com.example.zhongchou.rxpicture.bean.ImageFolder;
import com.example.zhongchou.rxpicture.ui.adapter.PickerAlbumAdapter;
import com.example.zhongchou.rxpicture.utils.DensityUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

public class PopWindowManager {

    private PopupWindow mAlbumPopWindow;
    private PickerAlbumAdapter albumAdapter;

    public void init(final TextView title, final List<ImageFolder> data) {
        albumAdapter = new PickerAlbumAdapter(data, DensityUtil.dp2px(title.getContext(), 80));
        albumAdapter.setDismissListener(view -> dismissAlbumWindow());
        title.setOnClickListener(view -> showPopWindow(view, data, albumAdapter));
    }

    private void showPopWindow(View v, List<ImageFolder> data, PickerAlbumAdapter albumAdapter) {
        if (mAlbumPopWindow == null) {
            View windowView = createWindowView(v, albumAdapter);
            mAlbumPopWindow = new PopupWindow(windowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            mAlbumPopWindow.setAnimationStyle(R.style.RxPicker_PopupAnimation);
            mAlbumPopWindow.setContentView(windowView);
            mAlbumPopWindow.setOutsideTouchable(true);
            mAlbumPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (mAlbumPopWindow.isShowing()) {
            dismissAlbumWindow();
            return;
        }
        mAlbumPopWindow.showAsDropDown(v, 0, 0);
    }

    @NonNull
    private View createWindowView(View clickView, PickerAlbumAdapter albumAdapter) {
        View view = LayoutInflater.from(clickView.getContext()).inflate(R.layout.rxpicker_item_popwindow_album, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.album_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(albumAdapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = AutoUtils.getPercentHeightSize(1);
            }
        });
        view.findViewById(R.id.layoutCancel).setOnClickListener(view1 -> dismissAlbumWindow());
        return view;
    }

    private void dismissAlbumWindow() {
        if (mAlbumPopWindow != null && mAlbumPopWindow.isShowing()) {
            mAlbumPopWindow.dismiss();
        }
    }
}
