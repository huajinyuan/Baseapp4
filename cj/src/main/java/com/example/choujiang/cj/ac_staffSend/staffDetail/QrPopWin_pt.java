package com.example.choujiang.cj.ac_staffSend.staffDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;


import com.example.choujiang.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by zuoyun on 2017/10/23.
 */

public class QrPopWin_pt extends PopupWindow {
    private Context context;
    private View view;
    CloseListener listener;

    public QrPopWin_pt(Context mContext){
        context = mContext;
        view = LayoutInflater.from(context).inflate(R.layout.item_popup_qr_pt, null);
        setContentView(view);
        setWidth(MATCH_PARENT);
        setHeight(MATCH_PARENT);
        setAnimationStyle(R.style.popup_qr);

        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.close();
            }
        });
    }

    public interface CloseListener{
        void close();
    }

    public void setCloseListener(CloseListener closeListener){
        listener = closeListener;
    }
}
