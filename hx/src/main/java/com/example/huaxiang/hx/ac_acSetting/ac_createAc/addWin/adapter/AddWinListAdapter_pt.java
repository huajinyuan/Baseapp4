package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addWin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addWin.AddWinListActivity_pt;
import com.example.huaxiang.hx.ac_memberget.m.CjHistory;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AddWinListAdapter_pt extends RecyclerView.Adapter<AddWinListAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<CjHistory> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AddWinListAdapter_pt(Context mContext, ArrayList<CjHistory> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_add_win_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        CjHistory cjHistory = data.get(position);

        holder.tv_mobile.setText("手机号：" + cjHistory.mobile);
        holder.tv_awardName.setText("奖品：￥" + cjHistory.price + "元" + cjHistory.awardName);

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("aaa", position + "");
//                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
//                data.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, data.size() - position);

                AddWinListActivity_pt.instance.deleteItem(cjHistory.id);
                
            }
        });

        holder.ll_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(cjHistory.id);
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
        TextView tv_mobile;
        TextView tv_awardName;
        TextView tv_delete;
        LinearLayout ll_view;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            tv_awardName = (TextView) itemView.findViewById(R.id.tv_awardName);
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
                AddWinListActivity_pt.instance.deleteItem(id);
            }
        });
    }
}
