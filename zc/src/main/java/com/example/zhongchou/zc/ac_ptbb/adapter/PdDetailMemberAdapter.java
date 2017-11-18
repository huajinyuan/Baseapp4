package com.example.zhongchou.zc.ac_ptbb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhongchou.R;
import com.example.zhongchou.zc.ac_ptbb.m.Member_pt;
import com.example.zhongchou.utils.UiUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class PdDetailMemberAdapter extends RecyclerView.Adapter<PdDetailMemberAdapter.AnchorHotViewHolder> {
    private ArrayList<Member_pt> data;
    private Context context;
    LayoutInflater layoutInflater;

    public PdDetailMemberAdapter(Context mContext, ArrayList<Member_pt> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_pddetail_member,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Member_pt member_pt = data.get(position);

        holder.tv_name.setText(member_pt.name);
        if (member_pt.ptDate != 0) {
            holder.tv_date.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(member_pt.ptDate));
        }
        UiUtil.setImage(holder.iv_head, member_pt.imgUrl);
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
        TextView tv_date;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);

        }
    }
}
