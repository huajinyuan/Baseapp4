package com.example.huaxiang.hx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.AcListActivity_pt;
import com.example.huaxiang.hx.ac_bb.AcBbActivity;
import com.example.huaxiang.hx.ac_bb.IntentionCustomerActivity;
import com.example.huaxiang.hx.ac_bb.RebackListActivity;
import com.example.huaxiang.hx.ac_bb.StaffRankingActivity;
import com.example.huaxiang.hx.ac_memberget.MemberGetActivity;
import com.example.huaxiang.hx.ac_staffSend.StaffSendActivity_pt;
import com.example.huaxiang.hx.ac_withdrawSetting.SettingActivity_pt;
import com.example.huaxiang.hx.m.AppMenu;
import com.example.huaxiang.hx.utils.DisplayMetricsUtil;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AppMenuAdapter extends RecyclerView.Adapter<AppMenuAdapter.AnchorHotViewHolder> {
    private ArrayList<AppMenu> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AppMenuAdapter(Context mContext, ArrayList<AppMenu> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_app_menu,
                parent, false);
        view.getLayoutParams().width = DisplayMetricsUtil.getScreenWidth(context) / 4;
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        AppMenu appMenu = data.get(position);

        holder.tv_menu.setText(appMenu.name);
        if (appMenu.sort.equals("10")) {
            holder.iv_menu.setImageResource(R.mipmap.icon_activity_list_hx);
        } else if (appMenu.sort.equals("20")) {
            holder.iv_menu.setImageResource(R.mipmap.icon_member_list_hx);
        } else if (appMenu.sort.equals("30")) {
            holder.iv_menu.setImageResource(R.mipmap.icon_intent_customer_hx);
        } else if (appMenu.sort.equals("40")) {
            holder.iv_menu.setImageResource(R.mipmap.icon_reback_hx);
        } else if (appMenu.sort.equals("110")) {
            holder.iv_menu.setImageResource(R.mipmap.icon_ac_setting_hx);
        } else if (appMenu.sort.equals("120")) {
            holder.iv_menu.setImageResource(R.mipmap.icon_setting_tc_hx);
        } else if (appMenu.sort.equals("130")) {
            holder.iv_menu.setImageResource(R.mipmap.icon_member_send_hx);
        } else if (appMenu.sort.equals("140")) {
            holder.iv_menu.setImageResource(R.mipmap.icon_user_get_hx);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (appMenu.sort) {
                    case "10":
                        context.startActivity(new Intent(context, AcBbActivity.class));
                        break;
                    case "20":
                        context.startActivity(new Intent(context, StaffRankingActivity.class));
                        break;
                    case "30":
                        context.startActivity(new Intent(context, IntentionCustomerActivity.class));
                        break;
                    case "40":
                        context.startActivity(new Intent(context, RebackListActivity.class));
                        break;
                    case "110":
                        context.startActivity(new Intent(context, AcListActivity_pt.class));
                        break;
                    case "120":
                        context.startActivity(new Intent(context, SettingActivity_pt.class));
                        break;
                    case "130":
                        context.startActivity(new Intent(context, StaffSendActivity_pt.class));
                        break;
                    case "140":
                        context.startActivity(new Intent(context, MemberGetActivity.class));
                        break;
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
        ImageView iv_menu;
        TextView tv_menu;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_menu = (ImageView) itemView.findViewById(R.id.iv_menu);
            tv_menu = (TextView) itemView.findViewById(R.id.tv_menu);
        }
    }

}
