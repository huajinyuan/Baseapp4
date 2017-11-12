package com.zt.pintuan.pt.ac_ptbb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.pt.ac_staffSend.m.Staff_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class StaffRankingAdapter_pt extends RecyclerView.Adapter<StaffRankingAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<Staff_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public StaffRankingAdapter_pt(Context mContext, ArrayList<Staff_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_staff_ranking_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Staff_pt staff_pt = data.get(position);

        holder.tv_name.setText(staff_pt.name);
        holder.tv_money.setText(staff_pt.drivingTurnover);
        holder.tv_peopleNum.setText(staff_pt.groupSize);
        holder.tv_succeedNum.setText(staff_pt.cliqueNumber);

        holder.tv_order.setText((position + 1) + "");
        if (position == 0 || position == 1 || position == 2) {
            holder.tv_order.setBackgroundResource(R.drawable.shape_radius3_blue);
        } else {
            holder.tv_order.setBackgroundResource(R.drawable.shape_radius3_a1);
        }
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
        TextView tv_money;
        TextView tv_order;
        TextView tv_peopleNum;
        TextView tv_succeedNum;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_order = (TextView) itemView.findViewById(R.id.tv_order);
            tv_peopleNum = (TextView) itemView.findViewById(R.id.tv_peopleNum);
            tv_succeedNum = (TextView) itemView.findViewById(R.id.tv_succeedNum);
        }
    }

}
