package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.m.HxTopic;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AddTopicAdapter extends RecyclerView.Adapter<AddTopicAdapter.AnchorHotViewHolder> {
    private ArrayList<HxTopic> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AddTopicAdapter(Context mContext, ArrayList<HxTopic> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_add_topic_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {

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
        TextView tv_type;
        EditText et_answer;
        ImageView iv_add_reduce;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            et_answer = (EditText) itemView.findViewById(R.id.et_answer);
            iv_add_reduce = (ImageView) itemView.findViewById(R.id.iv_add_reduce);
        }
    }

}
