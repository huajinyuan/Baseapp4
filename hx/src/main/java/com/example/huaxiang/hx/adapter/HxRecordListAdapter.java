package com.example.huaxiang.hx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.example.huaxiang.R;

import java.util.ArrayList;


/**
 * Created by  on 2016/9/2.
 */
public class HxRecordListAdapter extends RecyclerView.Adapter<HxRecordListAdapter.AnchorHotViewHolder> {
    private ArrayList<String> data;
    private Context context;
    LayoutInflater layoutInflater;

    public HxRecordListAdapter(Context mContext, ArrayList<String> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_repord_list,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class AnchorHotViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swip;
        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            swip = (SwipeLayout) itemView;
        }
    }

}
