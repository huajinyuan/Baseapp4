package com.zt.baseapp.pt.ac_withdrawSetting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.zt.baseapp.R;
import com.zt.baseapp.pt.ac_withdrawSetting.m.AccountDetail_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AccountDetailAdapter_pt extends RecyclerView.Adapter<AccountDetailAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<AccountDetail_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AccountDetailAdapter_pt(Context mContext, ArrayList<AccountDetail_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_account_detail_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        AccountDetail_pt detail = data.get(position);

        holder.tv_change.setText("+ ￥" + detail.change);
        holder.tv_type.setText(detail.type);
        holder.tv_balance.setText("￥" + detail.balance);
        holder.tv_date.setText(detail.date);
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
        TextView tv_change;
        TextView tv_type;
        TextView tv_balance;
        TextView tv_date;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_change = (TextView) itemView.findViewById(R.id.tv_change);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_balance = (TextView) itemView.findViewById(R.id.tv_balance);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
