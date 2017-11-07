package com.example.huaxiang.hx.ac_bb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.RebackDetailActivity;
import com.example.huaxiang.hx.ac_bb.m.Reback_hx;

import java.util.ArrayList;


/**
 * Created by  on 2016/9/2.
 */
public class RebackListAdapter extends RecyclerView.Adapter<RebackListAdapter.AnchorHotViewHolder> {
    private ArrayList<Reback_hx> data;
    private Context context;
    LayoutInflater layoutInflater;

    public RebackListAdapter(Context mContext, ArrayList<Reback_hx> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_reback_list_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Reback_hx reback_hx = data.get(position);

        holder.tv_awardName.setText("产品：" + reback_hx.awardName);
        holder.tv_date.setText(reback_hx.createDate);
        holder.tv_phone.setText("手机：" + reback_hx.phone);
        holder.tv_carNumber.setText("车牌：" + reback_hx.licensePlate);
        holder.tv_staff.setText("员工：" + reback_hx.user.name);

        holder.tv_reback.setText("回访：" + "暂无");
        if (reback_hx.recordList != null) {
            if (reback_hx.recordList.size() != 0) {
                holder.tv_reback.setText("回访：" + reback_hx.recordList.get(0).createDate + "  " + reback_hx.recordList.get(0).content + " [" + reback_hx.recordList.get(0).user.name + "]");
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RebackDetailActivity.class).putExtra("visitId", reback_hx.id));
            }
        });
        holder.iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "打call", Toast.LENGTH_SHORT).show();
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
        TextView tv_awardName;
        TextView tv_date;
        TextView tv_phone;
        ImageView iv_phone;
        TextView tv_carNumber;
        TextView tv_staff;
        TextView tv_reback;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_awardName = (TextView) itemView.findViewById(R.id.tv_awardName);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            iv_phone = (ImageView) itemView.findViewById(R.id.iv_phone);
            tv_carNumber = (TextView) itemView.findViewById(R.id.tv_carNumber);
            tv_staff = (TextView) itemView.findViewById(R.id.tv_staff);
            tv_reback = (TextView) itemView.findViewById(R.id.tv_reback);
        }
    }

}
