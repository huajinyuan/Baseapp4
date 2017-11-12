package com.example.huaxiang.hx.ac_bb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.IntentionCustomerActivity;
import com.example.huaxiang.hx.ac_bb.RebackListActivity;
import com.example.huaxiang.hx.ac_bb.StaffRankingActivity;
import com.example.huaxiang.hx.ac_staffSend.m.Activity_cj;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_ac_bb_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Activity_cj activity_cj = data.get(position);

        holder.tv_cjPrice.setVisibility(View.GONE);
        holder.tv_name.setText(activity_cj.name);
        holder.tv_totalNum.setText(activity_cj.totalNum + "");
        holder.tv_replaceNum.setText(activity_cj.replaceNum + "");
        holder.tv_intentionCount.setText(activity_cj.intentionCount + "");
        holder.tv_conversionCount.setText(activity_cj.conversionCount + "");

        holder.tv_staffRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffRankingActivity.class).putExtra("actId", activity_cj.actId + ""));
            }
        });
        holder.ll_totalNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, IntentionCustomerActivity.class));
            }
        });
        holder.ll_replaceNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, IntentionCustomerActivity.class));
            }
        });
        holder.ll_intentionCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, IntentionCustomerActivity.class));
            }
        });
        holder.ll_conversionCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RebackListActivity.class).putExtra("type", "2"));
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
        TextView tv_cjPrice;
        TextView tv_name;
        TextView tv_totalNum;
        TextView tv_replaceNum;
        TextView tv_intentionCount;
        TextView tv_conversionCount;
        TextView tv_staffRanking;

        LinearLayout ll_totalNum;
        LinearLayout ll_replaceNum;
        LinearLayout ll_intentionCount;
        LinearLayout ll_conversionCount;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_cjPrice = (TextView) itemView.findViewById(R.id.tv_cjPrice);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_totalNum = (TextView) itemView.findViewById(R.id.tv_totalNum);
            tv_replaceNum = (TextView) itemView.findViewById(R.id.tv_replaceNum);
            tv_intentionCount = (TextView) itemView.findViewById(R.id.tv_intentionCount);
            tv_conversionCount = (TextView) itemView.findViewById(R.id.tv_conversionCount);
            tv_staffRanking = (TextView) itemView.findViewById(R.id.tv_staffRanking);

            ll_totalNum = (LinearLayout) itemView.findViewById(R.id.ll_totalNum);
            ll_replaceNum = (LinearLayout) itemView.findViewById(R.id.ll_replaceNum);
            ll_intentionCount = (LinearLayout) itemView.findViewById(R.id.ll_intentionCount);
            ll_conversionCount = (LinearLayout) itemView.findViewById(R.id.ll_conversionCount);
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
