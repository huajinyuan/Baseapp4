package com.example.choujiang.cj.ac_acSetting.m;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类
 */
public class MyBitmapUtil {
    private static int THUMBNAIL_SIZE = 140;
    private static int NOMARL_SIZE = 800;


    public static boolean saveBitmap(Bitmap bitmap, File file) {
        return saveBitmap(bitmap, file, 50);
    }

    public static boolean saveBitmap(Bitmap bitmap, File file, int quality) {
        try {
            bitmap.compress(CompressFormat.JPEG, quality, new FileOutputStream(file));
            bitmap.recycle();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            System.gc();
        }
    }

    /**
     * 获取文件的路径
     *
     * @param
     * @return
     */
    public static String getFilePath(Context context, Uri uri) {

        String filePath = null;

        if (!TextUtils.isEmpty(uri.getAuthority())) {
            String[] projection = {Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
        } else {
            filePath = uri.getPath();
        }

        return filePath;
    }


    public static Bitmap getBitmap(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(imagePath, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.min(height, width); // 原图的最小边长
        if (minLen > 1280) { // 如果原始图像的最小边长大于1280dp
            inSampleSize = (int) minLen / 1280; // 计算像素压缩比例
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeFile(imagePath, options); // 解码文件
        Log.e("TAG", "size: " + bm.getByteCount() / 1000 + " width:" + bm.getWidth() + " heigth:" + bm.getHeight()); // 输出图像数据
        return bm;
    }

    /**
     * 把batmap 转file
     * @param bitmap
     * @param filepath
     */
    public static File saveBitmapFile(Bitmap bitmap, String filepath){
        File file=new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("Folder", "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
}
