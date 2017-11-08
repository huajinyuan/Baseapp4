package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addWin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addWin.AddWinActivity_pt;
import com.example.huaxiang.hx.ac_acSetting.ac_createAc.addWin.AwardListActivity_cj;
import com.example.huaxiang.hx.ac_acSetting.m.Award;
import com.example.huaxiang.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class AwardListAdapter_cj extends RecyclerView.Adapter<AwardListAdapter_cj.AnchorHotViewHolder> {
    private ArrayList<Award> data;
    private Context context;
    LayoutInflater layoutInflater;

    public AwardListAdapter_cj(Context mContext, ArrayList<Award> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_award_list_hx,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Award award = data.get(position);

        holder.tv_name.setText(award.name);
        holder.tv_price.setText("￥：" + award.price);
        UiUtil.setImage(holder.iv_ac, award.imgUrl);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWinActivity_pt.instance.award = award;
                AddWinActivity_pt.instance.et_award.setText("￥：" + award.price + "元" + award.name);
                AwardListActivity_cj.instance.finish();

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
        ImageView iv_ac;
        TextView tv_name;
        TextView tv_price;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_ac = (ImageView) itemView.findViewById(R.id.iv_ac);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
