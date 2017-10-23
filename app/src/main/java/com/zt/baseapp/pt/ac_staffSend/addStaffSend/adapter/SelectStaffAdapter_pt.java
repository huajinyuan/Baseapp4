package com.zt.baseapp.pt.ac_staffSend.addStaffSend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.baseapp.R;
import com.zt.baseapp.pt.ac_staffSend.m.Staff_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class SelectStaffAdapter_pt extends RecyclerView.Adapter<SelectStaffAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<Staff_pt> data;
    private Context context;
    LayoutInflater layoutInflater;
    int checkedPosition = 0;
    public int userId;

    public SelectStaffAdapter_pt(Context mContext, ArrayList<Staff_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_select_staff_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Staff_pt staff = data.get(position);

        holder.tv_name.setText(staff.name);
        holder.tv_shop.setText(staff.storeName);
        holder.cb_staff.setChecked(position == checkedPosition ? true : false);
        if (position == checkedPosition) {
            userId = staff.id;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedPosition = position;
                notifyDataSetChanged();
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
        CheckBox cb_staff;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_shop = (TextView) itemView.findViewById(R.id.tv_shop);
            cb_staff = (CheckBox) itemView.findViewById(R.id.cb_staff);
        }
    }
}
