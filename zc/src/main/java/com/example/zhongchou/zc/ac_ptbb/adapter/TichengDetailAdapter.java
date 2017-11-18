package com.example.zhongchou.zc.ac_ptbb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhongchou.R;
import com.example.zhongchou.zc.ac_ptbb.PdDetailActivity;
import com.example.zhongchou.zc.ac_ptbb.m.TichengDetail;

import java.util.ArrayList;


/**
 * Created by  on 2016/9/2.
 */
public class TichengDetailAdapter extends RecyclerView.Adapter<TichengDetailAdapter.AnchorHotViewHolder> {
    private ArrayList<TichengDetail> data;
    private Context context;
    LayoutInflater layoutInflater;

    public TichengDetailAdapter(Context mContext, ArrayList<TichengDetail> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_ticheng_detail_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        TichengDetail cjHistory = data.get(position);

        holder.tv_date.setText(cjHistory.createDate);
        holder.tv_number.setText(cjHistory.number);
        holder.tv_name.setText(cjHistory.actName);
        holder.tv_staff.setText(cjHistory.userName);
        holder.tv_business.setText(cjHistory.business + "");
        holder.tv_ticheng.setText("提成￥ " + cjHistory.commission);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PdDetailActivity.class).putExtra("orderNumber", cjHistory.orderNumber));
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
        TextView tv_date;
        TextView tv_number;
        TextView tv_status;
        TextView tv_name;
        TextView tv_business;
        TextView tv_staff;
        TextView tv_ticheng;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_business = (TextView) itemView.findViewById(R.id.tv_business);
            tv_staff = (TextView) itemView.findViewById(R.id.tv_staff);
            tv_ticheng = (TextView) itemView.findViewById(R.id.tv_ticheng);
        }
    }

}
