package com.example.choujiang.cj.ac_cjbb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_cjbb.m.CjHistory;

import java.util.ArrayList;


/**
 * Created by  on 2016/9/2.
 */
public class CjHistoryAdapter extends RecyclerView.Adapter<CjHistoryAdapter.AnchorHotViewHolder> {
    private ArrayList<CjHistory> data;
    private Context context;
    LayoutInflater layoutInflater;

    public CjHistoryAdapter(Context mContext, ArrayList<CjHistory> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_cj_history,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        CjHistory cjHistory = data.get(position);

        holder.tv_date.setText(cjHistory.createDate);
        holder.tv_number.setText(cjHistory.number);
        holder.tv_name.setText(cjHistory.actName);
        holder.tv_cjPerson.setText(cjHistory.userName);
//        holder.tv_getType.setText(cjHistory.awardName);
        holder.tv_staff.setText(cjHistory.memberName);

        switch (cjHistory.isAward) {
            case "0":
                holder.tv_status.setText("未中奖");
                holder.tv_status.setBackgroundResource(R.drawable.shape_radius3_appblue);
                holder.tv_jiangpin.setVisibility(View.INVISIBLE);
                break;
            case "1":
                holder.tv_status.setText("中奖");
                holder.tv_status.setBackgroundResource(R.drawable.shape_radius3_appred);
                holder.tv_jiangpin.setVisibility(View.VISIBLE);
                holder.tv_jiangpin.setText("奖品：" + cjHistory.awardName);
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
        TextView tv_cjPerson;
        TextView tv_getType;
        TextView tv_staff;
        TextView tv_jiangpin;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            cb_check = (CheckBox) itemView.findViewById(R.id.cb_check);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_cjPerson = (TextView) itemView.findViewById(R.id.tv_cjPerson);
            tv_getType = (TextView) itemView.findViewById(R.id.tv_getType);
            tv_staff = (TextView) itemView.findViewById(R.id.tv_staff);
            tv_jiangpin = (TextView) itemView.findViewById(R.id.tv_jiangpin);
        }
    }

}
