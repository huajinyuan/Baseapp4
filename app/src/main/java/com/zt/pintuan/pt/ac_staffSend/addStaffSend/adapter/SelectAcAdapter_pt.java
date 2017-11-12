package com.zt.pintuan.pt.ac_staffSend.addStaffSend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.pt.ac_staffSend.m.Activity_pt;
import com.zt.pintuan.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class SelectAcAdapter_pt extends RecyclerView.Adapter<SelectAcAdapter_pt.AnchorHotViewHolder> {
    public ArrayList<Activity_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public SelectAcAdapter_pt(Context mContext, ArrayList<Activity_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_select_ac_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Activity_pt activity_pt = data.get(position);

        UiUtil.setImage(holder.iv_head, activity_pt.imgUrl);
        holder.tv_name.setText(activity_pt.name);
        holder.tv_left.setText("剩余" + activity_pt.surplus);
        holder.cb_staff.setChecked(activity_pt.checked);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_pt.checked = !activity_pt.checked;
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
        TextView tv_left;
        CheckBox cb_staff;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_left = (TextView) itemView.findViewById(R.id.tv_left);
            cb_staff = (CheckBox) itemView.findViewById(R.id.cb_staff);
        }
    }
}
