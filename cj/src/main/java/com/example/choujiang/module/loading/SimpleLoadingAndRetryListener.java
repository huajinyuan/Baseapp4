package com.example.choujiang.module.loading;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.base.ReturnCode;


/**
 * Created by caiyk on 2017/4/25.
 */

public abstract class SimpleLoadingAndRetryListener extends OnLoadingAndRetryListener {
    private TextView tvRetryButton;
    private TextView tvRetryError;
    private ImageView ivRetryError;

    @Override
    public int generateLoadingLayoutId() {
        return R.layout.loading_loading;
    }

    @Override
    public int generateRetryLayoutId() {
        return R.layout.loading_retry_base;
    }

    @Override
    public int generateEmptyLayoutId() {
        return R.layout.loading_empty_base;
    }

    @Override
    public void setLoadingEvent(View loadingViewLayout) {

    }

    @Override
    public void setRetryEvent(View retryView) {
        ivRetryError = (ImageView) retryView.findViewById(R.id.ivRetryError);
        tvRetryError = (TextView) retryView.findViewById(R.id.tvRetryError);
        tvRetryButton = (TextView) retryView.findViewById(R.id.tvRetryButton);
        tvRetryButton.setOnClickListener(v -> onRetryClickListener());
    }

    @Override
    public void setRetryViewData(int code, String errorMsg) {
        if (code != ReturnCode.LOCAL_NO_NETWORK) {
        }
        tvRetryError.setText(errorMsg);
    }


    @Override
    public void showLoadingListener(boolean show) {

    }

    public abstract void onRetryClickListener();
}
