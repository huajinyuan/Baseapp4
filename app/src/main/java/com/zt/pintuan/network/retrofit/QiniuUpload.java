package com.zt.pintuan.network.retrofit;

import android.os.Environment;
import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.qiniu.android.utils.UrlSafeBase64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by gtgs on 2017/7/6.
 */

public class QiniuUpload {
    UploadManager uploadManager;
    private static QiniuUpload qiniuUpload;

    private QiniuUpload() {
        String dirPath = Environment.getExternalStorageDirectory().getPath() + "/Download";
        if (!new File(dirPath).exists()) {
            new File(dirPath).mkdirs();
        }
        Recorder recorder = null;
        try {
            File f = File.createTempFile("qiniu_xxxx", ".tmp");
            Log.d("qiniu", f.getAbsolutePath().toString());
            dirPath = f.getParent();
            recorder = new FileRecorder(dirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String dirPath1 = dirPath;
        //默认使用 key 的url_safe_base64编码字符串作为断点记录文件的文件名。
        //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
        KeyGenerator keyGen = new KeyGenerator() {
            public String gen(String key, File file) {
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                String path = key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
                Log.d("qiniu", path);
                File f = new File(dirPath1, UrlSafeBase64.encodeToString(path));
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(f));
                    String tempString = null;
                    int line = 1;
                    try {
                        while ((tempString = reader.readLine()) != null) {
//							System.out.println("line " + line + ": " + tempString);
                            Log.d("qiniu", "line " + line + ": " + tempString);
                            line++;
                        }

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        try {
                            reader.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return path;
            }
        };

        Configuration config = new Configuration.Builder()
                // recorder 分片上传时，已上传片记录器
                // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 实例化一个上传的实例
        uploadManager = new UploadManager(config);
    }

    public static QiniuUpload getInstance() {
        if (null == qiniuUpload) {
            qiniuUpload = new QiniuUpload();
        }
        return qiniuUpload;
    }

    /**
     *
     *
     * */

    public void uploadFile(String token, File file, UpCompletionHandler handler) {
//        data =<File对象、或 文件路径、或 字节数组>
//        String key =<指定七牛服务上的文件名，或 null >;
//        String token =<从服务端SDK获取 >;
//                new UpCompletionHandler() {
//                    @Override
//                    public void complete(String key, ResponseInfo info, JSONObject res) {
//                        //res包含hash、key等信息，具体字段取决于上传策略的设置
//                        if (info.isOK()) {
//                            Log.i("qiniu", "Upload Success");
//                        } else {
//                            Log.i("qiniu", "Upload Fail");
//                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
//                        }
//                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
//                    }
//                }
        uploadManager.put(file, null, token, handler, null);

    }


}
