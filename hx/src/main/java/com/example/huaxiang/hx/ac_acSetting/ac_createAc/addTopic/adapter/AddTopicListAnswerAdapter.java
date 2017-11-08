package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.m.HxTopic;

/**
 * Created by  on 2016/9/2.
 */
public class AddTopicListAnswerAdapter extends RecyclerView.Adapter<AddTopicListAnswerAdapter.AnchorHotViewHolder> {
    private HxTopic data;
    private Context context;
    LayoutInflater layoutInflater;
    String[] options;
    String[] rightAnswers;

    public AddTopicListAnswerAdapter(Context mContext, HxTopic mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
        options = data.option.split(";");
        rightAnswers = data.answer.split(",");
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_acdetail_topic_answer,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        holder.cb_option.setText(options[position]);

        boolean isRight = false;
        for (String str : rightAnswers) {
            if (((position + 1) + "").equals(str)) {
                isRight = true;
            }
        }
        holder.cb_option.setChecked(isRight);
    }


    @Override
    public int getItemCount() {
        return options.length;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class AnchorHotViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb_option;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            cb_option = (CheckBox) itemView.findViewById(R.id.cb_option);
        }
    }

}
