package com.example.choujiang.cj.ac_staffSend.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_staffSend.StaffSendActivity_pt;
import com.example.choujiang.cj.ac_staffSend.m.StaffSend_cj;
import com.example.choujiang.cj.ac_staffSend.staffDetail.StaffDetailActivity_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class StaffSendAdapter_pt extends RecyclerView.Adapter<StaffSendAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<StaffSend_cj> data;
    private Context context;
    LayoutInflater layoutInflater;

    public StaffSendAdapter_pt(Context mContext, ArrayList<StaffSend_cj> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_staff_send_cj,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        StaffSend_cj staffSend_cj = data.get(position);

        holder.tv_name.setText(staffSend_cj.userName);
        holder.tv_shop.setText(staffSend_cj.storeName);
        holder.tv_activityNum.setText(staffSend_cj.actCount + "");

//        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Log.e("aaa", position + "");
////                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
////                data.remove(position);
////                notifyItemRemoved(position);
////                notifyItemRangeChanged(position, data.size() - position);
//
//                StaffSendActivity_pt.instance.deleteItem(staffSend_cj.userId + "");
//            }
//        });

        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StaffDetailActivity_pt.class).putExtra("userId", staffSend_cj.userId + "").putExtra("storeId", staffSend_cj.storeId + ""));
            }
        });
        holder.ll_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(staffSend_cj.userId + "");
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
        ImageView iv_head;
        TextView tv_name;
        TextView tv_shop;
        TextView tv_activityNum;
        TextView tv_delete;

        LinearLayout ll_view;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_shop = (TextView) itemView.findViewById(R.id.tv_shop);
            tv_activityNum = (TextView) itemView.findViewById(R.id.tv_activityNum);
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
                StaffSendActivity_pt.instance.deleteItem(id);
            }
        });
    }
}
