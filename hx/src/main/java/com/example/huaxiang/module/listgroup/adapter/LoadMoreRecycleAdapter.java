package com.example.huaxiang.module.listgroup.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.huaxiang.R;
import com.example.huaxiang.module.listgroup.utils.ViewHolder;


/**
 * Created by caiyk on 2017/12/30.
 * Email:781208202@qq.com
 */

public class LoadMoreRecycleAdapter extends AbsRecycleAdapter {
    private static final int STATE_LOADING = 0;
    private static final int STATE_NO_MORE_DATA = 1;
    private static final int STATE_ERROR = 2;

    private int mLoadingState = STATE_LOADING;

    public LoadMoreRecycleAdapter(RecyclerView.Adapter adapter, boolean isLoadMore) {
        super(adapter);
        if (isLoadMore) {
            this.setLoadMoreView(R.layout.footer_more_default);
        }
    }

    public void setDataNoMore() {
        mLoadingState = STATE_NO_MORE_DATA;
        notifyItemChanged(getInnerItemCount());
    }

    public void setDataError() {
        mLoadingState = STATE_ERROR;
        notifyItemChanged(getInnerItemCount());
    }

    public void resetLoading(boolean isRefreshView) {
        mLoadingState = STATE_LOADING;
        if (isRefreshView) {
            notifyItemChanged(getInnerItemCount());
        }
    }

    @Override
    protected void convert(RecyclerView.ViewHolder holder, int position) {
        setViewState(mLoadingState, (ViewHolder) holder);
        ((ViewHolder) holder).setOnClickListener(R.id.rlRoot, v -> {
            if (mLoadingState == STATE_ERROR && mOnLoadMoreListener != null) {
                resetLoading(true);
                mOnLoadMoreListener.onLoadMoreRequested();
            }
        });
        if (mLoadingState == STATE_LOADING && mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMoreRequested();
        }
    }

    private void setViewState(int state, ViewHolder viewHolder) {
        viewHolder.setVisible(R.id.llLoading, state == STATE_LOADING);
        viewHolder.setVisible(R.id.tvNoData, state == STATE_NO_MORE_DATA);
        viewHolder.setVisible(R.id.tvError, state == STATE_ERROR);
    }
}
