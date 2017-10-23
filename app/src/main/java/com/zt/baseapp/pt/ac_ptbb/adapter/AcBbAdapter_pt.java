package com.zt.baseapp.pt.ac_ptbb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.pt.ac_ptList.AcInfoActivity_pt;
import com.zt.baseapp.pt.ac_staffSend.m.Activity_pt;
import com.zt.baseapp.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AcBbAdapter_pt extends RecyclerView.Adapter<AcBbAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<Activity_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AcBbAdapter_pt(Context mContext, ArrayList<Activity_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_ac_bb_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Activity_pt activity_pt = data.get(position);

        holder.tv_name.setText(activity_pt.name);
        holder.tv_money.setText(activity_pt.drivingTurnover);
        holder.tv_totalNum.setText(activity_pt.groupSize);
        holder.tv_succeed.setText(activity_pt.cliqueNumber);
        holder.tv_inProgress.setText(activity_pt.spellTogether);

        holder.tv_staffRanking.setOnClickListener(new View.OnClickListener() {
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
        TextView tv_name;
        TextView tv_money;
        TextView tv_totalNum;
        TextView tv_succeed;
        TextView tv_inProgress;
        TextView tv_staffRanking;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_totalNum = (TextView) itemView.findViewById(R.id.tv_totalNum);
            tv_succeed = (TextView) itemView.findViewById(R.id.tv_succeed);
            tv_inProgress = (TextView) itemView.findViewById(R.id.tv_inProgress);
            tv_staffRanking = (TextView) itemView.findViewById(R.id.tv_staffRanking);
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
