package com.example.choujiang.cj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.choujiang.R;


/**
 * Created by gtgs on 17/10/8.
 */

public class MemberlistAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
//    ArrayList<String>

    public MemberlistAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = inflater.inflate(R.layout.layout_member_list, null);
        return v;
    }
}
