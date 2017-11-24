package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.AddTopicActivity_cj;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.AddTopicListActivity_pt;
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

//        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AddTopicListActivity_pt.instance.deleteTopic(hxTopic.id);
//            }
//        });

        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddTopicActivity_cj.class).putExtra("hxTopic", hxTopic));
            }
        });

        holder.ll_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(hxTopic.id);
                return true;
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

        LinearLayout ll_view;
        TextView tv_delete;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_question = (TextView) itemView.findViewById(R.id.tv_question);
            rv_answer = (RecyclerView) itemView.findViewById(R.id.rv_answer);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            ll_view = (LinearLayout) itemView.findViewById(R.id.ll_view);
        }
    }

    public void showDeleteDialog(String id){
        final AlertDialog dialog_finish;
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.DialogTransBackGround);
        dialog_finish = builder.create();
        dialog_finish.setCancelable(true);
        dialog_finish.show();
        View view_dialog = LayoutInflater.from(context).inflate(R.layout.item_dialog_confirm, null);
        dialog_finish.setContentView(view_dialog);
        TextView tv_title = (TextView) view_dialog.findViewById(R.id.tv_dialog_title);
        TextView tv_content = (TextView) view_dialog.findViewById(R.id.tv_dialog_content);
        Button bt_yes = (Button) view_dialog.findViewById(R.id.bt_yes);
        Button bt_no = (Button) view_dialog.findViewById(R.id.bt_no);

        tv_title.setText("提示");
        tv_content.setText("是否删除？");
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_finish.dismiss();
            }
        });
        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_finish.dismiss();
                AddTopicListActivity_pt.instance.deleteTopic(id);
            }
        });
    }
}
