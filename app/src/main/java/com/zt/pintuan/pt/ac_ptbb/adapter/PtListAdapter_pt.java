package com.zt.pintuan.pt.ac_ptbb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.pt.ac_ptbb.m.Member_pt;
import com.zt.pintuan.pt.ac_ptbb.m.PinTuan_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class PtListAdapter_pt extends RecyclerView.Adapter<PtListAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<PinTuan_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public PtListAdapter_pt(Context mContext, ArrayList<PinTuan_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_pt_list_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        PinTuan_pt pinTuan_pt = data.get(position);

        holder.tv_date.setText(pinTuan_pt.createDate);
        holder.tv_number.setText(pinTuan_pt.orderNumber);
        holder.tv_name.setText(pinTuan_pt.actName);
        holder.tv_price.setText("￥" + pinTuan_pt.price);
        holder.tv_leader.setText(pinTuan_pt.head.name);
        holder.tv_member.setText(getMembers(pinTuan_pt.members));

        switch (pinTuan_pt.status) {
            case "1":
                holder.tv_status.setText("待成团");
                holder.tv_status.setBackgroundResource(R.drawable.shape_radius3_appblue);
                break;
            case "2":
                holder.tv_status.setText("已成团");
                holder.tv_status.setBackgroundResource(R.drawable.shape_radius3_appred);
                break;
            case "3":
                holder.tv_status.setText("失败");
                holder.tv_status.setBackgroundResource(R.drawable.shape_radius3_a1);
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
        TextView tv_price;
        TextView tv_leader;
        TextView tv_member;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            cb_check = (CheckBox) itemView.findViewById(R.id.cb_check);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_leader = (TextView) itemView.findViewById(R.id.tv_leader);
            tv_member = (TextView) itemView.findViewById(R.id.tv_member);
        }
    }

    String getMembers(ArrayList<Member_pt> member_pts){
        String str = "";
        for (Member_pt member : member_pts) {
            str += member.name + "，";
        }
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}
