package com.zt.pintuan.pt.ac_withdrawSetting.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.pt.ac_withdrawSetting.AccountDetailActivity_pt;
import com.zt.pintuan.pt.ac_withdrawSetting.m.Account_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AccountListAdapter_pt extends RecyclerView.Adapter<AccountListAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<Account_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AccountListAdapter_pt(Context mContext, ArrayList<Account_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_account_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Account_pt account = data.get(position);

        holder.tv_name.setText(account.name);
        holder.tv_commission.setText("总提成 ￥" + account.commission);
        holder.tv_balance.setText("￥" + account.balance);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AccountDetailActivity_pt.class));

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
        ImageView iv_head;
        TextView tv_name;
        TextView tv_commission;
        TextView tv_balance;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_commission = (TextView) itemView.findViewById(R.id.tv_commission);
            tv_balance = (TextView) itemView.findViewById(R.id.tv_balance);
        }
    }
}
