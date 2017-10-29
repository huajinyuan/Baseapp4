package com.example.choujiang.cj.ac_staffSend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_staffSend.m.Staff_cj;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class StaffAdapter_pt extends RecyclerView.Adapter<StaffAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<Staff_cj> data;
    private Context context;
    LayoutInflater layoutInflater;

    public StaffAdapter_pt(Context mContext, ArrayList<Staff_cj> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_staff_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Staff_cj staff = data.get(position);

        holder.tv_name.setText(staff.name);
        holder.tv_shop.setText(staff.storeName);

//        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("aaa", position + "");
//                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
//                data.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, data.size() - position);
//            }
//        });
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
//        TextView tv_delete;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_shop = (TextView) itemView.findViewById(R.id.tv_shop);
//            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
        }
    }
}
