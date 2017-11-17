package com.zt.pintuan.pt.ac_ptbb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.pt.ac_ptbb.PdListActivity_pt;
import com.zt.pintuan.pt.ac_ptbb.PtListActivity_pt;
import com.zt.pintuan.pt.ac_ptbb.StaffRankingActivity_pt;
import com.zt.pintuan.pt.ac_staffSend.m.Activity_pt;

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
        holder.tv_money.setText(activity_pt.drivingTurnover + "");
        holder.tv_totalNum.setText(activity_pt.groupSize + "");
        holder.tv_succeed.setText(activity_pt.cliqueNumber + "");
        holder.tv_inProgress.setText(activity_pt.spellTogether + "");
        holder.tv_status.setBackgroundResource(activity_pt.status == 1 ? R.drawable.shape_radius3_appblue : R.drawable.shape_radius3_a1);

        holder.tv_staffRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffRankingActivity_pt.class).putExtra("actId", activity_pt.id));
            }
        });
        holder.ll_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PdListActivity_pt.class));
            }
        });
        holder.ll_ptNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PdListActivity_pt.class));
            }
        });
        holder.ll_succeedNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PtListActivity_pt.class));
            }
        });
        holder.ll_inProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PtListActivity_pt.class));
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
        TextView tv_status;

        LinearLayout ll_money;
        LinearLayout ll_ptNum;
        LinearLayout ll_succeedNum;
        LinearLayout ll_inProgress;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_totalNum = (TextView) itemView.findViewById(R.id.tv_totalNum);
            tv_succeed = (TextView) itemView.findViewById(R.id.tv_succeed);
            tv_inProgress = (TextView) itemView.findViewById(R.id.tv_inProgress);
            tv_staffRanking = (TextView) itemView.findViewById(R.id.tv_staffRanking);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);

            ll_money = (LinearLayout) itemView.findViewById(R.id.ll_money);
            ll_ptNum = (LinearLayout) itemView.findViewById(R.id.ll_ptNum);
            ll_succeedNum = (LinearLayout) itemView.findViewById(R.id.ll_succeedNum);
            ll_inProgress = (LinearLayout) itemView.findViewById(R.id.ll_inProgress);
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
