package com.example.choujiang.cj.ac_withdrawSetting.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_staffSend.m.Staff_cj;
import com.example.choujiang.cj.ac_withdrawSetting.AccountDetailActivity_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AccountListAdapter_cj extends RecyclerView.Adapter<AccountListAdapter_cj.AnchorHotViewHolder> {
    private ArrayList<Staff_cj> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AccountListAdapter_cj(Context mContext, ArrayList<Staff_cj> mData) {
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
        Staff_cj account = data.get(position);

        holder.tv_name.setText(account.name);
        holder.tv_commission.setText("总提成 ￥" + account.totalWithdrawals);
        holder.tv_balance.setText("￥" + account.balance);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AccountDetailActivity_pt.class).putExtra("staff", account));

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
