package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.AddTopicActivity_cj;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addTopic.m.TopicEditData;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AddTopicAdapter extends RecyclerView.Adapter<AddTopicAdapter.AnchorHotViewHolder> {
    private ArrayList<TopicEditData> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AddTopicAdapter(Context mContext, ArrayList<TopicEditData> mData) {
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
        TopicEditData editData = data.get(position);
        holder.tv_type.setText(editData.isOption ? "选项" : "答案");
        holder.et_answer.setText(editData.text);
        holder.iv_add_reduce.setImageResource(position == data.size() - 1 ? R.mipmap.icon_circle_add : R.mipmap.icon_circle_reduce);

        holder.tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).isOption = data.get(position).isOption == true ? false : true;
                notifyItemChanged(position);

            }
        });
        holder.et_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTopicActivity_cj.instance.et_question.setCursorVisible(false);
                showDialogEdit(position, editData.text);
            }
        });
        holder.iv_add_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTopicActivity_cj.instance.et_question.setCursorVisible(false);

                if (position == data.size() - 1) {
                    data.add(new TopicEditData());
                    notifyDataSetChanged();
                    showDialogEdit(position + 1, "");

                } else {
                    data.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, data.size() - position);
                }
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

    void showDialogEdit(int position, String text) {
        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTransBackGround);
        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view_dialog = LayoutInflater.from(context).inflate(R.layout.item_dialog_edit, null);
        dialog.setContentView(view_dialog);

        final EditText et_content = (EditText) view_dialog.findViewById(R.id.et_content);
        Button bt_yes = (Button) view_dialog.findViewById(R.id.bt_yes);
        Button bt_no = (Button) view_dialog.findViewById(R.id.bt_no);

        et_content.setText(text);
        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_content.getText().toString().trim();
                if (!content.isEmpty()) {
                    dialog.dismiss();
                    data.get(position).text = content;
                    notifyDataSetChanged();
                }
            }
        });
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
