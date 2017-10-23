package com.example.choujiang.cj.ac_ptList.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.choujiang.R;
import com.example.choujiang.cj.ac_ptList.AcInfoActivity_pt;
import com.example.choujiang.cj.ac_staffSend.m.Activity_pt;
import com.example.choujiang.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AcListAdapter_pt extends RecyclerView.Adapter<AcListAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<Activity_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AcListAdapter_pt(Context mContext, ArrayList<Activity_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_staff_detail_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Activity_pt activity_pt = data.get(position);

        holder.tv_name.setText(activity_pt.name);
        holder.tv_money.setText("￥" + activity_pt.ptGood.price);
        holder.tv_peopleNum.setText(activity_pt.num + "");
        holder.tv_acNum.setText(activity_pt.saleNum);

        UiUtil.setImage(holder.iv_ac, activity_pt.imgUrl);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AcInfoActivity_pt.class).putExtra("id", activity_pt.id));
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
        ImageView iv_ac;
        TextView tv_ac_status;
        TextView tv_name;
        TextView tv_money;
        TextView tv_peopleNum;
        TextView tv_acNum;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_ac = (ImageView) itemView.findViewById(R.id.iv_ac);
            tv_ac_status = (TextView) itemView.findViewById(R.id.tv_ac_status);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_peopleNum = (TextView) itemView.findViewById(R.id.tv_peopleNum);
            tv_acNum = (TextView) itemView.findViewById(R.id.tv_acNum);
        }
    }
}
