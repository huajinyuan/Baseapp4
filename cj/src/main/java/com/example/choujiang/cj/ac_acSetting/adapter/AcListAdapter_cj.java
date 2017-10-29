package com.example.choujiang.cj.ac_acSetting.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.choujiang.R;
import com.example.choujiang.cj.ac_acSetting.AcInfoActivity_cj;
import com.example.choujiang.cj.ac_staffSend.m.Activity_cj;
import com.example.choujiang.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AcListAdapter_cj extends RecyclerView.Adapter<AcListAdapter_cj.AnchorHotViewHolder> {
    private ArrayList<Activity_cj> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AcListAdapter_cj(Context mContext, ArrayList<Activity_cj> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_ac_list_cj,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Activity_cj activity_cj = data.get(position);

        holder.tv_name.setText(activity_cj.name);
        holder.tv_available.setText("有效期：" + activity_cj.beginTime + " - " + activity_cj.endTime);
        UiUtil.setImage(holder.iv_ac, activity_cj.imgUrl);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AcInfoActivity_cj.class).putExtra("id", activity_cj.id));
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

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_ac = (ImageView) itemView.findViewById(R.id.iv_ac);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_available = (TextView) itemView.findViewById(R.id.tv_available);
        }
    }
}
