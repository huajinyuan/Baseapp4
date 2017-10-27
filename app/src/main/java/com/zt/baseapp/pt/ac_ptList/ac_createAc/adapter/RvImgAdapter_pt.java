package com.zt.baseapp.pt.ac_ptList.ac_createAc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zt.baseapp.R;
import com.zt.baseapp.utils.ScreenUtils;
import com.zt.baseapp.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class RvImgAdapter_pt extends RecyclerView.Adapter<RvImgAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<String> data;
    private Context context;
    LayoutInflater layoutInflater;

    public RvImgAdapter_pt(Context mContext, ArrayList<String> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_img_pt,
                parent, false);
        view.getLayoutParams().height = ScreenUtils.getScreenWidth() / 4;
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        if (data.size() == 8) {
            UiUtil.setImage(holder.iv_ac, data.get(position));
        } else {
            if (position == data.size()) {
                holder.iv_ac.setImageResource(R.mipmap.icon_img_add_pt);
                holder.iv_ac.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("aaa", "click");
                    }
                });
            } else {
                UiUtil.setImage(holder.iv_ac, data.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (data.size() == 8) {
            return 8;
        } else {
            return data.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class AnchorHotViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_ac;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            iv_ac = (ImageView) itemView.findViewById(R.id.iv_ac);
        }
    }
}
