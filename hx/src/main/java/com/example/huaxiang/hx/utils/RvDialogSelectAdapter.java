package com.example.huaxiang.hx.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.huaxiang.R;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class RvDialogSelectAdapter extends RecyclerView.Adapter<RvDialogSelectAdapter.AnchorHotViewHolder> {
    private ArrayList<String> data;
    private Context context;
    LayoutInflater layoutInflater;

    int selectPosition;
    SelectListener listener;

    public RvDialogSelectAdapter(Context mContext, ArrayList<String> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_dialog_select,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        holder.bt_select.setText(data.get(position));

        if (position == selectPosition) {
            holder.bt_select.setBackgroundResource(R.drawable.shape_radius3_appblue);
            holder.bt_select.setTextColor(ContextCompat.getColor(context, R.color.color_white));
        } else {
            holder.bt_select.setBackgroundResource(R.drawable.shape_border3_grey);
            holder.bt_select.setTextColor(ContextCompat.getColor(context, R.color.color_black_0D));
        }

        holder.bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition = position;
                notifyDataSetChanged();

                listener.select(position);
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
        Button bt_select;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            bt_select = (Button) itemView.findViewById(R.id.bt_select);
        }
    }

    public interface SelectListener{
        void select(int position);
    }
    public void SetSelectListener(SelectListener selectListener){
        listener = selectListener;
    }

    public void setSelectPosition(int position) {
        selectPosition = position;
        notifyDataSetChanged();
    }
}
