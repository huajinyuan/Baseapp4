package com.example.huaxiang.hx.ac_bb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.m.IntentionCustomer;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class IntentionCustomerAdapter extends RecyclerView.Adapter<IntentionCustomerAdapter.AnchorHotViewHolder> {
    private ArrayList<IntentionCustomer> data;
    private Context context;
    LayoutInflater layoutInflater;

    public IntentionCustomerAdapter(Context mContext, ArrayList<IntentionCustomer> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_intention_customer,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        IntentionCustomer cjHistory = data.get(position);

        holder.tv_date.setText(cjHistory.createDate);
        holder.tv_number.setText(cjHistory.number);
        holder.tv_cjPrice.setText(cjHistory.money + "块钱博");
        holder.tv_name.setText(cjHistory.actName);
        holder.tv_cjPerson.setText(cjHistory.memberName);
        holder.tv_replaceNum.setText(cjHistory.replaced + "");
        holder.tv_leftInvite.setText("还可邀约 " + (cjHistory.replaceTime - cjHistory.replaced) + " 人代抽");
        holder.tv_price.setText("￥" + cjHistory.money);

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
        TextView tv_date;
        TextView tv_number;
        TextView tv_cjPrice;
        TextView tv_name;
        TextView tv_cjPerson;
        TextView tv_replaceNum;
        TextView tv_leftInvite;
        TextView tv_price;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_cjPrice = (TextView) itemView.findViewById(R.id.tv_cjPrice);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_cjPerson = (TextView) itemView.findViewById(R.id.tv_cjPerson);
            tv_replaceNum = (TextView) itemView.findViewById(R.id.tv_replaceNum);
            tv_leftInvite = (TextView) itemView.findViewById(R.id.tv_leftInvite);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }

}
