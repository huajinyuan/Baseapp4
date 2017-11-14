package com.example.choujiang.cj.ac_cjbb;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_cjbb.adapter.CjHistoryAdapter;
import com.example.choujiang.cj.ac_cjbb.m.CjHistory;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;

import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(SearchPresenter_cj.class)
public class SearchActivity_cj extends BaseActivity<SearchPresenter_cj> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_right;
    EditText et_search;

    RecyclerView rv_staffSend;
    CjHistoryAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<CjHistory> pinDan_pts = new ArrayList<>();
    boolean canGet = true;
    int page = 1;

    String keyword = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_cj;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        et_search = (EditText) findViewById(R.id.et_search);
        rv_staffSend = (RecyclerView) findViewById(R.id.rv_staffSend);

    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);

        rv_staffSend.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        if(canGet)
                            getData();
            }
        });

    }

    void setRv(ArrayList<CjHistory> pinDans) {
        if (adapter == null) {
            pinDan_pts.clear();
            pinDan_pts.addAll(pinDans);
            adapter = new CjHistoryAdapter(context, pinDan_pts);
            layoutManager = new LinearLayoutManager(context);
            rv_staffSend.setLayoutManager(layoutManager);
            rv_staffSend.setAdapter(adapter);
        } else {
            pinDan_pts.addAll(pinDans);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_topbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    pinDan_pts.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    keyword = editable.toString();
                    refresh();
                }
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.searchCjHistory(token, page, 10, keyword), new Subscriber<Response<ArrayList<CjHistory>>>() {
            @Override
            public void onStart() {
                super.onStart();
                canGet = false;
            }

            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<ArrayList<CjHistory>> arrayListResponse) {
                setRv(arrayListResponse.data);
                canGet = true;
                page++;
            }
        });
    }

    void refresh(){
        adapter = null;
        page = 1;
        getData();
    }


}
