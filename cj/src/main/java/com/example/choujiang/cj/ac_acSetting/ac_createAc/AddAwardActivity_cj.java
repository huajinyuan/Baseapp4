package com.example.choujiang.cj.ac_acSetting.ac_createAc;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_acSetting.m.Award;
import com.example.choujiang.cj.ac_acSetting.m.MyBitmapUtil;
import com.example.choujiang.cj.ac_acSetting.m.QiniuToKen;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.network.retrofit.QiniuUpload;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;
import com.example.choujiang.utils.UiUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

import static com.example.choujiang.cj.m.StringConfig.QiniuBase;


@RequiresPresenter(AddAwardPresenter_cj.class)
public class AddAwardActivity_cj extends BaseActivity<AddAwardPresenter_cj> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    String imgUrl;
    String name, price, num, awardOdds;

    Button bt_save;
    EditText et_name;
    EditText et_price;
    EditText et_num;
    EditText et_odds;
    ImageView iv_ac;
    String sdcardPath;

    Award award;
    int position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_add_award_cj;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("奖品设置");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        et_name = (EditText) findViewById(R.id.et_name);
        et_price = (EditText) findViewById(R.id.et_price);
        et_num = (EditText) findViewById(R.id.et_num);
        et_odds = (EditText) findViewById(R.id.et_odds);
        iv_ac = (ImageView) findViewById(R.id.iv_ac);
        bt_save = findView(R.id.bt_save);

        getPermissions(this);
        sdcardPath = getApplicationContext().getFilesDir().getAbsolutePath();
//        sdcardPath = Environment.getExternalStorageDirectory().getPath();

        et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });
        et_odds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        award = (Award) getIntent().getSerializableExtra("award");
        position = getIntent().getIntExtra("position", 0);
        setData();
    }

    void setData(){
        if (award != null) {
            if (award.name != null) {
                et_name.setText(award.name);
            }
            if (award.price != 0) {
                et_price.setText(award.price + "");
            }
            if (award.num != 0) {
                et_num.setText(award.num + "");
            }
            if (award.awardOdds != 0) {
                et_odds.setText(award.awardOdds + "");
            }
            if (award.imgUrl != null) {
                imgUrl = award.imgUrl;
                UiUtil.setImage(iv_ac, imgUrl);
            }
        } else {
            award = new Award();
        }
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString().trim();
                price = et_price.getText().toString().trim();
                num = et_num.getText().toString().trim();
                awardOdds = et_odds.getText().toString().trim();

                if (name.isEmpty() || price.isEmpty() || num.isEmpty() || awardOdds.isEmpty()) {
                    Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show();
                } else if (imgUrl == null) {
                    Toast.makeText(context, "请上传图片", Toast.LENGTH_SHORT).show();
                } else {
                    price = price.equals(".") ? ".0" : price;
                    awardOdds = awardOdds.equals(".") ? ".0" : awardOdds;

                    if (position >= CreateAcActivity_cj.instance.data.awards.size()) {
                        addAward();
                    } else {
                        editAward();
                    }
                }
            }
        });
        findViewById(R.id.iv_ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotodialog();
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
        View view = LayoutInflater.from(this).inflate(R.layout.item_dialog_pickimg_takephoto_cj, null);
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
                    imgUrl = QiniuBase + res.getString("key");
                    UiUtil.setImage(iv_ac, imgUrl);
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

    void addAward(){
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAward(token, CreateAcActivity_cj.instance.data.id, name, price, num, awardOdds, imgUrl), new Subscriber<Response<Award>>() {
            @Override
            public void onStart() {
                super.onStart();
                bt_save.setClickable(false);
            }

            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
                bt_save.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
                bt_save.setClickable(true);
            }

            @Override
            public void onNext(Response<Award> arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    Toast.makeText(context, "添加奖品成功", Toast.LENGTH_SHORT).show();
                    CreateAcActivity_cj.instance.getData();
                    finish();
                }
            }
        });

    }
    void editAward(){
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAward(token, CreateAcActivity_cj.instance.data.id, name, price, num, awardOdds, imgUrl, award.id), new Subscriber<Response>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    Toast.makeText(context, "修改活动成功", Toast.LENGTH_SHORT).show();
                    CreateAcActivity_cj.instance.getData();
                    finish();
                }
            }
        });
    }

}
