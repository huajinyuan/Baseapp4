package com.zt.pintuan.pt.ac_ptList.ac_createAc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zt.pintuan.R;
import com.zt.pintuan.pt.ac_ptList.ac_createAc.CreateAcActivity_pt;
import com.zt.pintuan.pt.ac_ptList.ac_createAc.StoreListActivity_pt;
import com.zt.pintuan.pt.ac_ptList.m.Store_cj;

import java.util.ArrayList;

/**
 * Created by  on 2016/9/2.
 */
public class StoreListAdapter_pt extends RecyclerView.Adapter<StoreListAdapter_pt.AnchorHotViewHolder> {
    private ArrayList<Store_cj> data;
    private Context context;
    LayoutInflater layoutInflater;

    public StoreListAdapter_pt(Context mContext, ArrayList<Store_cj> mData) {
        data = mData;
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public AnchorHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_shop_pt,
                parent, false);
        return new AnchorHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnchorHotViewHolder holder, final int position) {
        Store_cj shop = data.get(position);

        holder.tv_name.setText(shop.storeName);
        holder.tv_phone.setText(shop.storeMobile);
        holder.tv_address.setText(shop.storeAddress);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAcActivity_pt.instance.activity_pt.merchantAddress = shop.storeAddress;
                CreateAcActivity_pt.instance.activity_pt.storeId = shop.id;
                CreateAcActivity_pt.instance.activity_pt.storeName = shop.storeName;
                CreateAcActivity_pt.instance.activity_pt.storePhone = shop.storeMobile;
                CreateAcActivity_pt.instance.setData();
                StoreListActivity_pt.instance.finish();
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
        TextView tv_phone;
        TextView tv_address;

        public AnchorHotViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }
}
