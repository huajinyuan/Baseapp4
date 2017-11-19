package com.example.huaxiang.hx.ac_staffSend.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_staffSend.StaffSendActivity_pt;
import com.example.huaxiang.hx.ac_staffSend.m.StaffSend_hx;
import com.example.huaxiang.hx.ac_staffSend.staffDetail.StaffDetailActivity_pt;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class StaffSendAdapter_pt extends RecyclerView.Adapter<StaffSendAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<StaffSend_hx> data;
    private Context context;
    LayoutInflater layoutInflater;

    public StaffSendAdapter_pt(Context mContext, ArrayList<StaffSend_hx> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_staff_send_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        StaffSend_hx staffSend_hx = data.get(position);
        holder.tv_name.setText(staffSend_hx.userName);
        holder.tv_shop.setText(staffSend_hx.storeName);
        holder.tv_activityNum.setText(staffSend_hx.actCount);

//        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Log.e("aaa", position + "");
////                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
////                data.remove(position);
////                notifyItemRemoved(position);
////                notifyItemRangeChanged(position, data.size() - position);
//
//                StaffSendActivity_pt.instance.deleteItem(staffSend_hx.userId + "");
//            }
//        });

        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StaffDetailActivity_pt.class);
                intent.putExtra("userId", staffSend_hx.userId + "");
                intent.putExtra("staffSendId", staffSend_hx.id);
                context.startActivity(intent);
            }
        });
        holder.ll_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(staffSend_hx.userId + "");
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

    void showDeleteDialog(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTransBackGround);
        final AlertDialog mydialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.item_dialog_delete, null);
        mydialog.show();
        mydialog.setContentView(view);

        // dialog内部的点击事件
        view.findViewById(R.id.bt_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialog.dismiss();
                StaffSendActivity_pt.instance.deleteItem(id);
            }
        });
    }
}
