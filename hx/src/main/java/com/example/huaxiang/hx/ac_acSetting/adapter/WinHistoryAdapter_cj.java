package com.example.huaxiang.hx.ac_acSetting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_memberget.m.CjHistory;

import java.util.ArrayList;


/**
 * Created by  on 2016/9/2.
 */
public class WinHistoryAdapter_cj extends RecyclerView.Adapter<WinHistoryAdapter_cj.AnchorHotViewHolder> {
    private ArrayList<CjHistory> data;
    private Context context;
    LayoutInflater layoutInflater;
    ClickListener listener;

    public WinHistoryAdapter_cj(Context mContext, ArrayList<CjHistory> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_win_history_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        CjHistory activity_pt = data.get(position);

        holder.tv_name.setText(activity_pt.mobile);
        holder.tv_jiangpin.setText(activity_pt.awardName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click();
            }
        });
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
        TextView tv_name;
        TextView tv_jiangpin;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_jiangpin = (TextView) itemView.findViewById(R.id.tv_jiangpin);
        }
    }

    public interface ClickListener{
        void click();
    }
    public void setClickListener(ClickListener mListener){
        listener = mListener;
    }
}
