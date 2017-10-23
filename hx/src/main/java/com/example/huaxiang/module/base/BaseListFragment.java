package com.example.huaxiang.module.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import com.example.huaxiang.R;
import com.example.huaxiang.base.ReturnCode;
import com.example.huaxiang.module.listgroup.adapter.LoadMoreRecycleAdapter;
import com.example.huaxiang.module.loading.LoadingAndRetryManager;
import com.example.huaxiang.module.loading.SimpleLoadingAndRetryListener;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * Created by caiyk on 2017/1/12.
 * Email:781208202@qq.com
 */

public abstract class BaseListFragment<D, P extends BaseListPresenter> extends BaseFragment<P>
        implements LoadMoreRecycleAdapter.OnLoadMoreListener, IListView<D> {

    private LoadingAndRetryManager mLoadingAndRetryManager;
    private RecyclerView mRecyclerView;
    private PtrFrameLayout mRefreshLayout;
    private LoadMoreRecycleAdapter mLoadMoreRecycleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewStub listViewStub = getListViewStub();
        listViewStub.setLayoutResource(R.layout.group_recycle_listview);
        View listLayout = listViewStub.inflate();
        mLoadingAndRetryManager = getLoadingAndRetryManager(listLayout);
        mRecyclerView = (RecyclerView) listLayout.findViewById(R.id.my_recycler_view);
        mRefreshLayout = (PtrFrameLayout) listLayout.findViewById(R.id.refreshLayout);
        initRefreshLayout(mRefreshLayout);
        initRecycleView(mRecyclerView);
        if (mRecyclerView.getLayoutManager() == null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
        mRefreshLayout.setEnabled(getLoadMethod() == EnumLoadMethod.ALL || getLoadMethod() == EnumLoadMethod.ONLY_PULL_DOWN);
        mRefreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestRefreshData(false);
            }
        });

        mLoadMoreRecycleAdapter = new LoadMoreRecycleAdapter(getListAdapter(getPresenter().mDataList), getLoadMethod() == EnumLoadMethod.ALL || getLoadMethod() == EnumLoadMethod.ONLY_LOAD_MORE);
        mLoadMoreRecycleAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mLoadMoreRecycleAdapter);
        if (savedInstanceState == null) {
            requestRefreshData(true);
        } else {
            showLoadingPage();
        }
    }

    private LoadingAndRetryManager getLoadingAndRetryManager(View listLayout) {
        return LoadingAndRetryManager.generate(listLayout, new SimpleLoadingAndRetryListener() {
            @Override
            public void onRetryClickListener() {
                requestRefreshData(true);
            }
        });
    }

    private void initRefreshLayout(PtrFrameLayout refreshLayout) {
    }

    protected void initRecycleView(RecyclerView recyclerView) {

    }

    protected void requestRefreshData(boolean showLoadingPage) {
        if (mLoadMoreRecycleAdapter != null) {
            mLoadMoreRecycleAdapter.resetLoading(false);
        }
        if (showLoadingPage) {
            showLoadingPage();
        }
        getPresenter().requestListData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.refreshComplete();
        }
        getPresenter().requestListData(false);
    }

    @Override
    public void showListItems(boolean refresh, List<D> dataList) {
        showLoadingContent();
        showLoadingContent();
        mLoadMoreRecycleAdapter.notifyDataSetChanged();
        if (refresh) {
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.refreshComplete();
            }
        }
    }

    @Override
    public void showLoadMoreState(int code, String msg) {
        showLoadingContent();
        if (code != ReturnCode.CODE_SUCCESS) {
            mLoadMoreRecycleAdapter.setDataError();
        } else {
            mLoadMoreRecycleAdapter.setDataNoMore();
        }
    }

    @Override
    public void showLoadingPage() {
        if (mLoadingAndRetryManager != null) {
            mLoadingAndRetryManager.showLoadingPage();
        }
    }

    @Override
    public void showLoadingRetry(int code, String errorMsg) {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.refreshComplete();
        }
        if (mLoadingAndRetryManager != null) {
            mLoadingAndRetryManager.showLoadingRetry(code, errorMsg);
        }
    }

    @Override
    public void showLoadingEmpty() {
        if (mLoadingAndRetryManager != null) {
            mLoadingAndRetryManager.showLoadingEmpty();
        }
    }

    @Override
    public void showLoadingContent() {
        if (mLoadingAndRetryManager != null) {
            mLoadingAndRetryManager.showLoadingContent();
        }
    }

    protected EnumLoadMethod getLoadMethod() {
        return EnumLoadMethod.ALL;
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    protected abstract ViewStub getListViewStub();

    protected abstract RecyclerView.Adapter getListAdapter(List<D> dataList);
}
