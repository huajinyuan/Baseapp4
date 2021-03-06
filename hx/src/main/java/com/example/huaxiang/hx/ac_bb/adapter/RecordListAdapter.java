package com.example.huaxiang.hx.ac_bb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.m.Record;

import java.util.ArrayList;


/**
 * Created by  on 2016/9/2.
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.AnchorHotViewHolder> {
    private ArrayList<Record> data;
    private Context context;
    LayoutInflater layoutInflater;

    public RecordListAdapter(Context mContext, ArrayList<Record> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_record_list_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Record record = data.get(position);

        holder.tv_staff.setText("员工：" + record.user.name);
        holder.tv_date.setText(record.createDate);
        holder.tv_content.setText(record.content);

        holder.view_line.setVisibility(position == data.size() - 1 ? View.INVISIBLE : View.VISIBLE);

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
        TextView tv_staff;
        TextView tv_date;
        TextView tv_content;
        View view_line;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_staff = (TextView) itemView.findViewById(R.id.tv_staff);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            view_line = itemView.findViewById(R.id.view_line);
        }
    }

}
