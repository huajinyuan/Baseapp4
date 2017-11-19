package com.example.huaxiang.hx.ac_bb.adapter;

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
public class ReplaceListAdapter extends RecyclerView.Adapter<ReplaceListAdapter.AnchorHotViewHolder> {
    private ArrayList<CjHistory> data;
    private Context context;
    LayoutInflater layoutInflater;

    public ReplaceListAdapter(Context mContext, ArrayList<CjHistory> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_replace_list,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        CjHistory cjHistory = data.get(position);

        if (cjHistory.member != null) {
            holder.tv_name.setText(cjHistory.member.name);
        }
        holder.tv_awardName.setText(cjHistory.awardName);
        holder.tv_date.setText(cjHistory.createDate.length() > 10 ? cjHistory.createDate.substring(0, 10) : cjHistory.createDate);
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
        TextView tv_awardName;
        TextView tv_date;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_awardName = (TextView) itemView.findViewById(R.id.tv_awardName);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

}
