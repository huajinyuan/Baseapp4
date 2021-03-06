package com.example.choujiang.cj.ac_acSetting.ac_createAc;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_acSetting.m.MyBitmapUtil;
import com.example.choujiang.cj.ac_acSetting.m.QiniuToKen;
import com.example.choujiang.cj.ac_staffSend.m.Activity_cj;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.network.retrofit.QiniuUpload;
import com.example.choujiang.rxpicture.widget.cropview.CropImageView;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;
import com.example.choujiang.utils.UiUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

import static com.example.choujiang.cj.m.StringConfig.QiniuBase;


@RequiresPresenter(AddAcPresenter_cj.class)
public class AddAcActivity_cj extends BaseActivity<AddAcPresenter_cj> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    String imgUrl;
    String strDate;

    EditText et_name;
    EditText et_beginTime;
    EditText et_endTime;
    EditText et_cjNum;
    EditText et_shareGetNum;
    EditText et_remark;
    ImageView iv_ac;
    String sdcardPath;

    String name, beginTime, endTime, cjNum, shareGetNum, remark;

    RelativeLayout rl_cuticon;
    ImageView iv_cut_back;
    TextView tv_cut_right;
    CropImageView cropView;
    Bitmap bitmap1000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_add_ac_cj;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("活动设置");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        et_name = (EditText) findViewById(R.id.et_name);
        et_beginTime = (EditText) findViewById(R.id.et_beginTime);
        et_endTime = (EditText) findViewById(R.id.et_endTime);
        et_cjNum = (EditText) findViewById(R.id.et_cjNum);
        et_shareGetNum = (EditText) findViewById(R.id.et_shareGetNum);
        et_remark = (EditText) findViewById(R.id.et_remark);
        iv_ac = (ImageView) findViewById(R.id.iv_ac);

        rl_cuticon = findView(R.id.rl_cuticon);
        iv_cut_back = findView(R.id.iv_cut_back);
        tv_cut_right = findView(R.id.tv_cut_right);
        cropView = findView(R.id.cropView);

        getPermissions(this);
//        sdcardPath = getApplicationContext().getFilesDir().getAbsolutePath();
        sdcardPath = Environment.getExternalStorageDirectory().getPath();
    }

    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        setData();
    }

    void setData(){
        if (CreateAcActivity_cj.instance.data != null) {
            if (CreateAcActivity_cj.instance.data.name != null) {
                et_name.setText(CreateAcActivity_cj.instance.data.name);
            }
            if (CreateAcActivity_cj.instance.data.beginTime != null) {
                et_beginTime.setText(CreateAcActivity_cj.instance.data.beginTime);
            }
            if (CreateAcActivity_cj.instance.data.endTime != null) {
                et_endTime.setText(CreateAcActivity_cj.instance.data.endTime);
            }
            if (CreateAcActivity_cj.instance.data.count != 0) {
                et_cjNum.setText(CreateAcActivity_cj.instance.data.count + "");
            }
            if (CreateAcActivity_cj.instance.data.shareCount != 0) {
                et_shareGetNum.setText(CreateAcActivity_cj.instance.data.shareCount + "");
            }
            if (CreateAcActivity_cj.instance.data.remarks != null) {
                et_remark.setText(CreateAcActivity_cj.instance.data.remarks);
            }
            if (CreateAcActivity_cj.instance.data.imgUrl != null) {
                imgUrl = CreateAcActivity_cj.instance.data.imgUrl;
                UiUtil.setImage(iv_ac, imgUrl);
            }
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
                beginTime = et_beginTime.getText().toString().trim();
                endTime = et_endTime.getText().toString().trim();
                cjNum = et_cjNum.getText().toString().trim();
                shareGetNum = et_shareGetNum.getText().toString().trim();
                remark = et_remark.getText().toString().trim();

                if (name.isEmpty() || beginTime.isEmpty() || endTime.isEmpty() || cjNum.isEmpty() || shareGetNum.isEmpty() || remark.isEmpty()) {
                    Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show();
                } else if(getDateLong(beginTime)>getDateLong(endTime)){
                    Toast.makeText(context, "结束时间不能早于开始时间", Toast.LENGTH_SHORT).show();
                } else {
                    if (CreateAcActivity_cj.instance.data.id == null) {
                        addAc();
                    } else {
                        editAc();
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
        findViewById(R.id.et_beginTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDatePicker(et_beginTime);
            }
        });
        findViewById(R.id.et_endTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDatePicker(et_endTime);
            }
        });

        iv_cut_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_cuticon.setVisibility(View.GONE);
            }
        });
        tv_cut_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(new File(mPhotoPath).exists())
                        new File(mPhotoPath).delete();

                    FileOutputStream os = new FileOutputStream(mPhotoPath);
                    cropView.getCroppedBitmap().compress(Bitmap.CompressFormat.PNG, 90, os);
                    rl_cuticon.setVisibility(View.GONE);

                    if (new File(mPhotoPath).exists()) {
                        uploadphoto(new File(mPhotoPath));
                    } else {
                        Toast.makeText(context,"裁剪失败",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
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
//            uploadphoto(MyBitmapUtil.saveBitmapFile(MyBitmapUtil.getBitmap(pathurl), mPhotoPath));

            rl_cuticon.setVisibility(View.VISIBLE);
            bitmap1000 = MyBitmapUtil.getBitmap(pathurl);

            cropView.setCustomRatio(2, 1);
            cropView.setInitialFrameScale(0.9f);
            cropView.setImageBitmap(bitmap1000);
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

    public void showDialogDatePicker(EditText et) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTransBackGround);
        final AlertDialog dialog_date = builder.create();
        dialog_date.show();
        View view_dialog = LayoutInflater.from(context).inflate(R.layout.item_dialog_datepicker, null);
        dialog_date.setContentView(view_dialog);
        DatePicker picker = (DatePicker) view_dialog.findViewById(R.id.date_picker);
        Button bt_yes = (Button) view_dialog.findViewById(R.id.bt_yes);
        Button bt_no = (Button) view_dialog.findViewById(R.id.bt_no);

        //---------setCalender
        Calendar calendar = Calendar.getInstance();
        int int_Year = calendar.get(Calendar.YEAR);
        int int_Month = calendar.get(Calendar.MONTH);
        int int_Day = calendar.get(Calendar.DAY_OF_MONTH);
        strDate = int_Year + "-" + String.format("%02d", (int_Month + 1)) + "-" + String.format("%02d", int_Day);

        picker.init(int_Year, int_Month, int_Day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                Log.e("aaa", "" + i2);
                strDate = i + "-" + String.format("%02d", (i1 + 1)) + "-" + String.format("%02d", i2);
            }
        });

        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(strDate);
                dialog_date.dismiss();
            }
        });
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_date.dismiss();
            }
        });
    }

    void addAc(){
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAc(token, name, imgUrl, beginTime, endTime, cjNum, shareGetNum, "0", remark), new Subscriber<Response<Activity_cj>>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError1" + e.getMessage());
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Response<Activity_cj> arrayListResponse) {
                if (arrayListResponse.data != null) {
                    Toast.makeText(context, "添加活动成功", Toast.LENGTH_SHORT).show();
                    CreateAcActivity_cj.instance.id = arrayListResponse.data.id;
                    CreateAcActivity_cj.instance.getData();
                    finish();
                } else {
                    Toast.makeText(context, arrayListResponse.msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    void editAc(){
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAc(token, name, imgUrl, beginTime, endTime, cjNum, shareGetNum, "0", remark, CreateAcActivity_cj.instance.data.id), new Subscriber<Response>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError2" + e.getMessage());
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Response arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    Toast.makeText(context, "修改活动成功", Toast.LENGTH_SHORT).show();
                    CreateAcActivity_cj.instance.getData();
                    finish();
                } else {
                    Toast.makeText(context, arrayListResponse.msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    long getDateLong(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(str);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }
}
