package com.example.huaxiang.hx.ac_bb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_bb.m.HxTopic;

/**
 * Created by  on 2016/9/2.
 */
public class CjDetailTopicAnswerAdapter extends RecyclerView.Adapter<CjDetailTopicAnswerAdapter.AnchorHotViewHolder> {
    private HxTopic data;
    private Context context;
    LayoutInflater layoutInflater;
    String[] answers;
    String[] options;
    String[] rightAnswers;

    public CjDetailTopicAnswerAdapter(Context mContext, HxTopic mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
        answers = data.hxTopicMember.answer.split(",");
        options = data.option.split(";");
        rightAnswers = data.answer.split(",");
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_cjdetail_topic_answer,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        int optionPosition = Integer.parseInt(answers[position]) - 1;
        holder.tv_answer.setText(options[optionPosition]);

        boolean isRight = false;
        for (String str : rightAnswers) {
            if (answers[position].equals(str)) {
                isRight = true;
            }
        }
        holder.iv_answer.setImageResource(isRight ? R.mipmap.icon_answer_right : R.mipmap.icon_answer_wrong);
    }


    @Override
    public int getItemCount() {
        return answers.length;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class AnchorHotViewHolder extends RecyclerView.ViewHolder {
        TextView tv_answer;
        ImageView iv_answer;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_answer = (TextView) itemView.findViewById(R.id.tv_answer);
            iv_answer = (ImageView) itemView.findViewById(R.id.iv_answer);
        }
    }

}
