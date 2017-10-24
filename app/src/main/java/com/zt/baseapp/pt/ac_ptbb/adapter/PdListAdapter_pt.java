package com.zt.baseapp.pt.ac_ptbb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.pt.ac_ptbb.m.PinDan_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class PdListAdapter_pt extends RecyclerView.Adapter<PdListAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<PinDan_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public PdListAdapter_pt(Context mContext, ArrayList<PinDan_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_pd_list_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        PinDan_pt pinDan_pt = data.get(position);

        holder.tv_date.setText(pinDan_pt.createDate);
        holder.tv_number.setText(pinDan_pt.redeemCode);
        holder.tv_name.setText(pinDan_pt.goodName);
        holder.tv_price.setText("￥" + pinDan_pt.price);
        holder.tv_leader.setText(pinDan_pt.member.name + "[" + pinDan_pt.member.mobile + "]");
        holder.tv_payType.setText(pinDan_pt.type.equals("1") ? "微信支付" : "会员支付");

        switch (pinDan_pt.status) {
            case "1":
                holder.tv_status.setText("待成团");
                holder.tv_status.setBackgroundResource(R.drawable.shape_radius3_appblue);
                break;
            case "2":
                holder.tv_status.setText("已成团");
                holder.tv_status.setBackgroundResource(R.drawable.shape_radius3_appred);
                break;
            case "3":
                holder.tv_status.setText("失败");
                holder.tv_status.setBackgroundResource(R.drawable.shape_radius3_a1);
                break;
            case "4":
                holder.tv_status.setText("退款");
                holder.tv_status.setBackgroundResource(R.drawable.shape_radius3_a1);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        CheckBox cb_check;
        TextView tv_date;
        TextView tv_number;
        TextView tv_status;
        TextView tv_name;
        TextView tv_price;
        TextView tv_leader;
        TextView tv_payType;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            cb_check = (CheckBox) itemView.findViewById(R.id.cb_check);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_leader = (TextView) itemView.findViewById(R.id.tv_leader);
            tv_payType = (TextView) itemView.findViewById(R.id.tv_payType);
        }
    }

}
