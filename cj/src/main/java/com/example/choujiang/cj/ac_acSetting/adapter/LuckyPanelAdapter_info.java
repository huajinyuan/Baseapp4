package com.example.choujiang.cj.ac_acSetting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_acSetting.m.Award;
import com.example.choujiang.cj.utils.DisplayMetricsUtil;
import com.example.choujiang.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class LuckyPanelAdapter_info extends RecyclerView.Adapter<LuckyPanelAdapter_info.AnchorHotViewHolder> {
    public ArrayList<Award> data = new ArrayList<>();
    private Context context;
    LayoutInflater layoutInflater;

    public LuckyPanelAdapter_info(Context mContext) {
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
        for(int i=0;i<8;i++) {
            Award award = new Award();
            data.add(award);
        }
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_luckypanel_info, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_luckypanel_position4_info, parent, false);
        }

        view.getLayoutParams().width = (DisplayMetricsUtil.getScreenWidth(context) - DisplayMetricsUtil.dip2px(context, 88)) / 3;
        view.getLayoutParams().height = (DisplayMetricsUtil.getScreenWidth(context) - DisplayMetricsUtil.dip2px(context, 88)) / 3;
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        if (position != 4) {
            if (position > 4) {
                Award award = data.get(position - 1);
                if (award.name != null) {
                    holder.tv_name.setText(award.name);
                }
                if (award.imgUrl != null) {
                    UiUtil.setImage(holder.iv_award, award.imgUrl);
                }
            } else {
                Award award = data.get(position);
                if (award.name != null) {
                    holder.tv_name.setText(award.name);
                }
                if (award.imgUrl != null) {
                    UiUtil.setImage(holder.iv_award, award.imgUrl);
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return 9;
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 4) {
            return 1;
        } else {
            return 2;
        }
    }

    class AnchorHotViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_award;
        TextView tv_name;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_award = (ImageView) itemView.findViewById(R.id.iv_award);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    public void setAwards(ArrayList<Award> awards) {
        int max = awards.size() > 8 ? 8 : awards.size();
        for(int i=0;i<max;i++) {
            data.get(i).name = awards.get(i).name;
            data.get(i).imgUrl = awards.get(i).imgUrl;
        }
        notifyDataSetChanged();
    }
}
