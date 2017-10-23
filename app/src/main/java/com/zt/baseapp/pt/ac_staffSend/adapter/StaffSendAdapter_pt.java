package com.zt.baseapp.pt.ac_staffSend.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zt.baseapp.R;
import com.zt.baseapp.pt.ac_staffSend.m.StaffSend_pt;
import com.zt.baseapp.pt.ac_staffSend.staffDetail.StaffDetailActivity_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class StaffSendAdapter_pt extends RecyclerView.Adapter<StaffSendAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<StaffSend_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public StaffSendAdapter_pt(Context mContext, ArrayList<StaffSend_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_staff_send_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        StaffSend_pt staffSend_pt = data.get(position);

        holder.tv_name.setText(staffSend_pt.user.name);
        holder.tv_shop.setText(staffSend_pt.storeName);
        holder.tv_activityNum.setText(staffSend_pt.actCount);

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("aaa", position + "");
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, data.size() - position);
            }
        });

        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffDetailActivity_pt.class).putExtra("userId", staffSend_pt.user.id));
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
        TextView tv_shop;
        TextView tv_activityNum;
        TextView tv_delete;

        LinearLayout ll_view;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_shop = (TextView) itemView.findViewById(R.id.tv_shop);
            tv_activityNum = (TextView) itemView.findViewById(R.id.tv_activityNum);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);

            ll_view = (LinearLayout) itemView.findViewById(R.id.ll_view);
        }
    }
}
