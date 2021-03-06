package com.example.huaxiang.hx.ac_bb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_staffSend.m.Staff_cj;

import java.util.ArrayList;


/**
 * Created by  on 2016/9/2.
 */
public class StaffRankingAdapter extends RecyclerView.Adapter<StaffRankingAdapter.AnchorHotViewHolder> {
    private ArrayList<Staff_cj> data;
    private Context context;
    LayoutInflater layoutInflater;

    public StaffRankingAdapter(Context mContext, ArrayList<Staff_cj> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_staff_ranking_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Staff_cj staff_cj = data.get(position);

        holder.tv_name.setText(staff_cj.username);
        holder.tv_lotteryNumber.setText(staff_cj.lotteryNumber + "");
        holder.tv_replaceNum.setText(staff_cj.replaceNum + "");
        holder.tv_totalNum.setText(staff_cj.totalNum + "");

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
        TextView tv_order;
        TextView tv_lotteryNumber;
        TextView tv_replaceNum;
        TextView tv_totalNum;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_order = (TextView) itemView.findViewById(R.id.tv_order);
            tv_lotteryNumber = (TextView) itemView.findViewById(R.id.tv_lotteryNumber);
            tv_replaceNum = (TextView) itemView.findViewById(R.id.tv_replaceNum);
            tv_totalNum = (TextView) itemView.findViewById(R.id.tv_totalNum);
        }
    }

}
