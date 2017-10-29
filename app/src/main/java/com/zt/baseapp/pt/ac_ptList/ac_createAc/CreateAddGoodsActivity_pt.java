package com.zt.baseapp.pt.ac_ptList.ac_createAc;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.zt.baseapp.R;
import com.zt.baseapp.model.Response;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.network.retrofit.HttpMethods;
import com.zt.baseapp.network.retrofit.QiniuUpload;
import com.zt.baseapp.pt.ac_ptList.ac_createAc.adapter.RvImgAdapter_pt;
import com.zt.baseapp.pt.ac_ptList.m.MyBitmapUtil;
import com.zt.baseapp.pt.ac_ptList.m.QiniuToKen;
import com.zt.baseapp.pt.ac_staffSend.m.Goods_pt;
import com.zt.baseapp.utils.ACache;
import com.zt.baseapp.utils.ACacheKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

import static com.zt.baseapp.pt.m.StringConfig.QiniuBase;

@RequiresPresenter(CreateAddGoodsPresenter_pt.class)
public class CreateAddGoodsActivity_pt extends BaseActivity<CreateAddGoodsPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    String imgUrl;

    EditText et_name;
    EditText et_oldPrice;
    EditText et_price;
    EditText et_num;
    EditText et_url;
    RecyclerView rv_imgs;
    ArrayList<String> list_imgs=new ArrayList<>();
    String sdcardPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_add_goods_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("设置商品");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("编辑");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        et_name = (EditText) findViewById(R.id.et_name);
        et_oldPrice = (EditText) findViewById(R.id.et_oldPrice);
        et_price = (EditText) findViewById(R.id.et_price);
        et_num = (EditText) findViewById(R.id.et_num);
        et_url = (EditText) findViewById(R.id.et_url);
        rv_imgs = (RecyclerView) findViewById(R.id.rv_imgs);

        getPermissions(this);
        sdcardPath = getApplicationContext().getFilesDir().getAbsolutePath();
//        sdcardPath = Environment.getExternalStorageDirectory().getPath();
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        imgUrl = getIntent().getStringExtra("imgUrl");

        if (imgUrl != null) {
            String[] imgs = imgUrl.split(",");
            if (imgs.length != 0) {
                for (String str : imgs) {
                    list_imgs.add(str);
                }
            }
        }
        setRv_imgs();

    }

    void setRv_imgs(){
        RvImgAdapter_pt adapter = new RvImgAdapter_pt(context, list_imgs);
        adapter.setClickListener(new RvImgAdapter_pt.ClickListener() {
            @Override
            public void click() {
                showPhotodialog();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
        rv_imgs.setLayoutManager(layoutManager);
        rv_imgs.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imgUrl = "";
        for (String str : list_imgs) {
            imgUrl += str + ",";
        }
        if (imgUrl.length() > 0) {
            imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
        }

        String name, oldPrice, price, num;
        name = et_name.getText().toString().trim();
        oldPrice = et_oldPrice.getText().toString().trim();
        price = et_price.getText().toString().trim();
        num = et_num.getText().toString().trim();

        Goods_pt goods_pt = new Goods_pt();
        goods_pt.name = name;
        goods_pt.originalPrice = oldPrice;
        if(!price.isEmpty())
            goods_pt.price = Double.parseDouble(price);
        if(!num.isEmpty())
            goods_pt.count = Integer.parseInt(num);
        goods_pt.imgUrl = imgUrl;

        CreateAcActivity_pt.instance.activity_pt.ptGood = goods_pt;
        CreateAcActivity_pt.instance.setData();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void getPermissions(Activity mActivity){
        String[] PERMISSIONS_STORAGE = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.CAMERA"};
        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("permission", ">23");
            try {
                //检测是否有写的权限
                int permissionCAMERA = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA);
                if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
                    Log.e("permission", "permission 1");
                    // 没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(mActivity, PERMISSIONS_STORAGE, 1);
                }
            } catch (Exception e) {
                Log.e("permission", "permission 2" + e.toString());
                e.printStackTrace();
            }
        }
    }

    public void showPhotodialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTransBackGround);
        final AlertDialog mydialog = builder.create();
        View view = LayoutInflater.from(this).inflate(R.layout.item_dialog_pickimg_takephoto_pt, null);
        mydialog.show();
        mydialog.setContentView(view);

        // dialog内部的点击事件
        view.findViewById(R.id.bt_dialog_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        view.findViewById(R.id.bt_dialog_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
                mydialog.dismiss();
            }
        });
    }

    String mPhotoPath;
    File mPhotoFile;
    void takePicture() {
        mPhotoPath = sdcardPath + "/icon.png";
        mPhotoFile = new File(mPhotoPath);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", mPhotoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } catch (Exception e) {

            }
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
        }
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPhotoPath = sdcardPath + "/icon.png";
            String pathurl = null;
            if (requestCode == 1) {
                pathurl = mPhotoPath;
            } else {
                Uri uri = data.getData();
                pathurl = MyBitmapUtil.getFilePath(context, uri);
            }
            uploadphoto(MyBitmapUtil.saveBitmapFile(MyBitmapUtil.getBitmap(pathurl), mPhotoPath));
        }
    }

    //qiniu
    public void uploadphoto(final File file) {
        HttpMethods.start(HttpMethods.getInstance().demoService.getQiniuToken(token), new Subscriber<Response<QiniuToKen>>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<QiniuToKen> arrayListResponse) {
                String qiniuToken = arrayListResponse.data.token;
                if (qiniuToken != null) {
                    QiniuUpload.getInstance().uploadFile(qiniuToken, file, handler);
                }
            }
        });
    }

    UpCompletionHandler handler = new UpCompletionHandler() {
        @Override
        public void complete(String key, ResponseInfo info, JSONObject res) {
            //res包含hash、key等信息，具体字段取决于上传策略的设置
            if (info.isOK()) {
                try {
                    Log.e("qiniu", "Upload Success token:" + res.getString("key"));
                    list_imgs.add(QiniuBase + res.getString("key"));
                    setRv_imgs();
                } catch (JSONException e) {
                    Log.e("qiniu", "Upload Success, token getString wrong!");
                    Toast.makeText(context, "Upload Exception", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Upload Fail", Toast.LENGTH_SHORT).show();
                Log.e("aaa", info.toString()+" error:"+info.error);
            }
        }
    };
}
