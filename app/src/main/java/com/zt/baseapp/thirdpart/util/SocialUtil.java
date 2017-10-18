package com.zt.baseapp.thirdpart.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SocialUtil {
    private static final int THUMB_WIDTH = 120, THUMB_HEIGHT = 120;

    /**
     * 读取bitmap
     *
     * @param bmp
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp) {
        FutureTask<byte[]> future = new FutureTask<byte[]>(new Callable<byte[]>() {
            @Override
            public byte[] call() throws Exception {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                bmp.compress(CompressFormat.PNG, 100, output);
                bmp.recycle();

                byte[] result = output.toByteArray();
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
        new Thread(future).start();

        byte[] result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否为null，null则返回""
     *
     * @param str
     * @return
     */
    public static String handleStr(String str) {
        return str == null ? "" : str;
    }


    //    创建WXMediamessage.thumbData
    public static void buildThumbBmp(WXMediaMessage msg, Bitmap bmp) {
        if (bmp == null) {
            return;
        }
        Bitmap tmpBmp = Bitmap.createBitmap(bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(tmpBmp, THUMB_WIDTH, THUMB_HEIGHT, true);
//        tmpBmp.recycle(); // 避免对传入的参数图片进行修改，可以通过clone的方式进行解决该问题。
        msg.thumbData = SocialUtil.bmpToByteArray(thumbBmp);
    }
}
