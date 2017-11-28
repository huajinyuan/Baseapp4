package com.example.huaxiang.hx.ac_staffSend.staffDetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;

import java.io.File;
import java.io.FileOutputStream;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by zuoyun on 2017/10/23.
 */

public class QrPopWin_pt extends PopupWindow {
    private Context context;
    private View view;
    CloseListener listener;
    ImageView iv_staff_qr_pt;
    Bitmap bitmap;
    TextView tv_name;

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
        view.findViewById(R.id.tv_saveQr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    String savePath = Environment.getExternalStorageDirectory().getPath() + "/二维码/";
                    String mPhotoPath = savePath + "二维码.png";
                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    try {
                        FileOutputStream os = new FileOutputStream(mPhotoPath);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                        os.close();
                        if (new File(mPhotoPath).exists()) {
                            Toast.makeText(context, "已保存至 /二维码/二维码.png", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });

        iv_staff_qr_pt = (ImageView) view.findViewById(R.id.iv_staff_qr_pt);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
    }

    public interface CloseListener{
        void close();
    }

    public void setCloseListener(CloseListener closeListener){
        listener = closeListener;
    }

    public void setQr(Bitmap bitmapQr){
        bitmap = bitmapQr;
        iv_staff_qr_pt.setImageBitmap(bitmap);
    }
    public void setName(String name){
        tv_name.setText(name);
    }

}
