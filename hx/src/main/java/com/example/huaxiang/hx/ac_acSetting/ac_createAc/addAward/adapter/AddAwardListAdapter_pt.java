package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addAward.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addAward.AddAwardActivity_cj;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addAward.AddAwardListActivity_pt;
import com.example.huaxiang.hx.ac_acSetting.m.Award;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AddAwardListAdapter_pt extends RecyclerView.Adapter<AddAwardListAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<Award> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AddAwardListAdapter_pt(Context mContext, ArrayList<Award> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_add_award_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Award award = data.get(position);

        holder.tv_name.setText(award.name);
        holder.tv_num.setText("奖品数：" + award.num);
        holder.tv_awardOdds.setText("抽奖中奖率：" + (award.awardOdds * 100) + "%");
        holder.tv_replaceAwardOdds.setText("代抽中奖率：" + (award.replaceAwardOdds * 100) + "%");

//        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Log.e("aaa", position + "");
////                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
////                data.remove(position);
////                notifyItemRemoved(position);
////                notifyItemRangeChanged(position, data.size() - position);
//
//            }
//        });

        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddAwardActivity_cj.class).putExtra("award", award));
            }
        });
        holder.ll_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(award.id);
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
        TextView tv_name;
        TextView tv_num;
        TextView tv_awardOdds;
        TextView tv_replaceAwardOdds;

        LinearLayout ll_view;
        TextView tv_delete;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            tv_awardOdds = (TextView) itemView.findViewById(R.id.tv_awardOdds);
            tv_replaceAwardOdds = (TextView) itemView.findViewById(R.id.tv_replaceAwardOdds);

            ll_view = (LinearLayout) itemView.findViewById(R.id.ll_view);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
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
                AddAwardListActivity_pt.instance.deleteAward(id);
            }
        });
    }
}
