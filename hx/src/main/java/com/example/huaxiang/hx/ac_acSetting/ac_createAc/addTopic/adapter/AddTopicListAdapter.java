package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.m.HxTopic;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AddTopicListAdapter extends RecyclerView.Adapter<AddTopicListAdapter.AnchorHotViewHolder> {
    private ArrayList<HxTopic> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AddTopicListAdapter(Context mContext, ArrayList<HxTopic> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_add_topic_list,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        HxTopic hxTopic = data.get(position);
        holder.tv_question.setText((position + 1) + ". " + hxTopic.question);

        if (hxTopic.option != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            holder.rv_answer.setLayoutManager(layoutManager);
            holder.rv_answer.setAdapter(new AddTopicListAnswerAdapter(context,hxTopic));
        }

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        TextView tv_question;
        RecyclerView rv_answer;
        TextView tv_delete;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_question = (TextView) itemView.findViewById(R.id.tv_question);
            rv_answer = (RecyclerView) itemView.findViewById(R.id.rv_answer);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
        }
    }

}
