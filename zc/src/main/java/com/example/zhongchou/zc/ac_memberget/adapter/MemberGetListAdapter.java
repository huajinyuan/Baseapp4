package com.example.zhongchou.zc.ac_memberget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhongchou.R;
import com.example.zhongchou.zc.ac_ptbb.m.PinDan_pt;
import com.example.zhongchou.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class MemberGetListAdapter extends RecyclerView.Adapter<MemberGetListAdapter.AnchorHotViewHolder> {
    private ArrayList<PinDan_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public MemberGetListAdapter(Context mContext, ArrayList<PinDan_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_membergetlist_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        PinDan_pt cjHistory = data.get(position);

        UiUtil.setImage(holder.iv_ac, cjHistory.redeemCodeUrl);
        holder.tv_name.setText(cjHistory.goodName);
        holder.tv_num.setText("数量 "+cjHistory.goodNum);
        holder.tv_code.setText(cjHistory.redeemCode);
        holder.tv_personName.setText(cjHistory.member.name);
        holder.tv_date.setText(cjHistory.createDate);

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
        TextView tv_num;
        TextView tv_code;
        TextView tv_personName;
        TextView tv_date;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_ac = (ImageView) itemView.findViewById(R.id.iv_ac);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            tv_code = (TextView) itemView.findViewById(R.id.tv_code);
            tv_personName = (TextView) itemView.findViewById(R.id.tv_personName);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
