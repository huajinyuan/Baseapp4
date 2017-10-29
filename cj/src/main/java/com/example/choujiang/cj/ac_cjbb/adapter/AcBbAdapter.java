package com.example.choujiang.cj.ac_cjbb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_cjbb.CjHistoryActivity;
import com.example.choujiang.cj.ac_cjbb.StaffRankingActivity;
import com.example.choujiang.cj.ac_staffSend.m.Activity_cj;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AcBbAdapter extends RecyclerView.Adapter<AcBbAdapter.AnchorHotViewHolder> {
    private ArrayList<Activity_cj> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AcBbAdapter(Context mContext, ArrayList<Activity_cj> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_ac_bb_cj,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Activity_cj activity_cj = data.get(position);

        holder.tv_name.setText(activity_cj.actName);
        holder.tv_totalManCount.setText(activity_cj.totalManCount + "");
        holder.tv_totalShareCount.setText(activity_cj.totalShareCount + "");
        holder.tv_totalCount.setText(activity_cj.totalCount + "");
//        holder.tv_totalAwardCount.setText(activity_cj.totalAwardCount + "");

        holder.tv_staffRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffRankingActivity.class).putExtra("actId", activity_cj.actId));
            }
        });
        holder.ll_totalManCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CjHistoryActivity.class));
            }
        });
        holder.ll_totalShareCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CjHistoryActivity.class));
            }
        });
        holder.ll_totalCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CjHistoryActivity.class));
            }
        });
        holder.ll_totalAwardCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CjHistoryActivity.class));
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
        TextView tv_name;
        TextView tv_totalManCount;
        TextView tv_totalShareCount;
        TextView tv_totalCount;
        TextView tv_totalAwardCount;
        TextView tv_staffRanking;

        LinearLayout ll_totalManCount;
        LinearLayout ll_totalShareCount;
        LinearLayout ll_totalCount;
        LinearLayout ll_totalAwardCount;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_totalManCount = (TextView) itemView.findViewById(R.id.tv_totalManCount);
            tv_totalShareCount = (TextView) itemView.findViewById(R.id.tv_totalShareCount);
            tv_totalCount = (TextView) itemView.findViewById(R.id.tv_totalCount);
            tv_totalAwardCount = (TextView) itemView.findViewById(R.id.tv_totalAwardCount);
            tv_staffRanking = (TextView) itemView.findViewById(R.id.tv_staffRanking);

            ll_totalManCount = (LinearLayout) itemView.findViewById(R.id.ll_totalManCount);
            ll_totalShareCount = (LinearLayout) itemView.findViewById(R.id.ll_totalShareCount);
            ll_totalCount = (LinearLayout) itemView.findViewById(R.id.ll_totalCount);
            ll_totalAwardCount = (LinearLayout) itemView.findViewById(R.id.ll_totalAwardCount);
        }
    }

    String getStatus(String status){
        if (status.equals("1")) {
            return "进行中";
        } else if (status.equals("2")) {
            return "暂停中";
        } else {
            return "作废";
        }
    }
}
