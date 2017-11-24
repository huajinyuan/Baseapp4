package com.example.huaxiang.hx.ac_staffSend.addStaffSend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_staffSend.m.Activity_cj;
import com.example.huaxiang.utils.UiUtil;

import java.util.ArrayList;


/**
 * Created by  on 2016/9/2.
 */
public class SelectAcAdapter_pt extends RecyclerView.Adapter<SelectAcAdapter_pt.AnchorHotViewHolder> {
    public ArrayList<Activity_cj> data;
    private Context context;
    LayoutInflater layoutInflater;

    public SelectAcAdapter_pt(Context mContext, ArrayList<Activity_cj> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
        data.get(0).checked = true;
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_select_ac_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Activity_cj activity_cj = data.get(position);

        holder.tv_name.setText(activity_cj.name);
        holder.tv_available.setText("剩余：" + activity_cj.surplus);
        UiUtil.setImage(holder.iv_ac, activity_cj.imgUrl);
        holder.cb_staff.setChecked(activity_cj.checked);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_cj.checked = !activity_cj.checked;
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
        ImageView iv_ac;
        TextView tv_name;
        TextView tv_available;
        CheckBox cb_staff;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_ac = (ImageView) itemView.findViewById(R.id.iv_ac);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_available = (TextView) itemView.findViewById(R.id.tv_available);
            cb_staff = (CheckBox) itemView.findViewById(R.id.cb_staff);
        }
    }
}
