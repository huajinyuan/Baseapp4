package com.example.huaxiang.hx.ac_acSetting.ac_createAc.addAward;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaxiang.R;
import com.example.huaxiang.hx.ac_acSetting.m.Award;
import com.example.huaxiang.hx.ac_acSetting.m.MyBitmapUtil;
import com.example.huaxiang.hx.ac_acSetting.m.QiniuToKen;
import com.example.huaxiang.model.Response;
import com.example.huaxiang.module.base.BaseActivity;
import com.example.huaxiang.network.retrofit.HttpMethods;
import com.example.huaxiang.network.retrofit.QiniuUpload;
import com.example.huaxiang.utils.ACache;
import com.example.huaxiang.utils.ACacheKey;
import com.example.huaxiang.utils.UiUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

import static com.example.huaxiang.hx.utils.StringConfig.QiniuBase;


@RequiresPresenter(AddAwardPresenter_cj.class)
public class AddAwardActivity_cj extends BaseActivity<AddAwardPresenter_cj> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    String imgUrl;
    String name, price, num, awardOdds, replaceOdds;

    EditText et_name;
    EditText et_price;
    EditText et_num;
    EditText et_odds;
    EditText et_replaceOdds;
    ImageView iv_ac;
    String sdcardPath;

    Award award;
    String id;

    RelativeLayout rl_cuticon;
    ImageView iv_cut_back;
    TextView tv_cut_right;
    ImageView iv_icon;
    Matrix matrix = new Matrix();
    int mode=0;
    int DRAG=1;
    int ZOOM=2;
    PointF startPoint = new PointF();   //起始点
    float startDis = 0;
    Bitmap bitmap1000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_add_award_hx;
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
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_hx);

        et_name = (EditText) findViewById(R.id.et_name);
        et_price = (EditText) findViewById(R.id.et_price);
        et_num = (EditText) findViewById(R.id.et_num);
        et_odds = (EditText) findViewById(R.id.et_odds);
        et_replaceOdds = (EditText) findViewById(R.id.et_replaceOdds);
        iv_ac = (ImageView) findViewById(R.id.iv_ac);

        rl_cuticon = findView(R.id.rl_cuticon);
        iv_cut_back = findView(R.id.iv_cut_back);
        tv_cut_right = findView(R.id.tv_cut_right);
        iv_icon = findView(R.id.iv_icon);

        getPermissions(this);
//        sdcardPath = getApplicationContext().getFilesDir().getAbsolutePath();
        sdcardPath = Environment.getExternalStorageDirectory().getPath();

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
        et_replaceOdds.addTextChangedListener(new TextWatcher() {
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
        id = getIntent().getStringExtra("id");
        if (award != null) {
            setData();
        }
    }

    void setData(){
        if (award.name != null) {
            et_name.setText(award.name);
            Log.e("aaa", award.name);
            Log.e("aaa", award.name.length() + "");
            et_name.setSelection(award.name.length());
        }
        et_price.setText(award.price + "");
        et_num.setText(award.num + "");
        et_odds.setText((award.awardOdds * 100) + "");
        et_replaceOdds.setText((award.replaceAwardOdds * 100) + "");
        if (award.imgUrl != null) {
            imgUrl = award.imgUrl;
            UiUtil.setImage(iv_ac, imgUrl);
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
            public void onClick(View view) {
                save();
            }
        });
        findViewById(R.id.iv_ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotodialog();
            }
        });

        iv_cut_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_icon.setImageBitmap(null);
                rl_cuticon.setVisibility(View.GONE);
            }
        });
        tv_cut_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(new File(mPhotoPath).exists())
                        new File(mPhotoPath).delete();
                }catch (Exception e){

                }

                matrix.postTranslate(0, 0);//选中中心位置
                try {
                    Bitmap smallbitmap = Bitmap.createBitmap(720, 720, Bitmap.Config.RGB_565);
                    Canvas canvas = new Canvas(smallbitmap);
                    canvas.drawColor(0xffffffff);
                    canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
                    canvas.drawBitmap(bitmap1000, matrix, null);
                    FileOutputStream os = new FileOutputStream(mPhotoPath);
                    smallbitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
                    os.close();
                    matrix.reset();//清空matrix

                    iv_icon.setImageBitmap(null);
                    rl_cuticon.setVisibility(View.GONE);
                    if((bitmap1000!=null)){//释放bitmap_normal
                        bitmap1000.recycle();
                        bitmap1000=null;
                    }

                    if (new File(mPhotoPath).exists()) {
                        uploadphoto(new File(mPhotoPath));
                    } else {
                        Toast.makeText(context,"裁剪失败",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("dda", "wrong at 确定裁剪");
                }
            }
        });
    }

    void save(){
        name = et_name.getText().toString().trim();
        price = et_price.getText().toString().trim();
        num = et_num.getText().toString().trim();
        awardOdds = et_odds.getText().toString().trim();
        replaceOdds = et_replaceOdds.getText().toString().trim();

        if (imgUrl == null) {
            imgUrl = "";
        }
        if (name.isEmpty() || price.isEmpty() || num.isEmpty() || awardOdds.isEmpty() || replaceOdds.isEmpty()) {
            Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show();
        } else {
            price = price.equals(".") ? "0" : price;
            awardOdds = awardOdds.equals(".") ? "0" : awardOdds;
            replaceOdds = replaceOdds.equals(".") ? "0" : replaceOdds;

            if (award == null) {
                addAward();
            } else {
                editAward();
            }
        }
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
            //uploadphoto(MyBitmapUtil.saveBitmapFile(MyBitmapUtil.getBitmap(pathurl), mPhotoPath));

            matrix.reset();
            rl_cuticon.setVisibility(View.VISIBLE);
            bitmap1000 = MyBitmapUtil.getBitmap(pathurl);
            iv_icon.setImageBitmap(bitmap1000);
            int oldwidth = bitmap1000.getWidth();
            int oldheight = bitmap1000.getHeight();

            float scale = oldwidth >= oldheight ? 720f / oldheight: 720f / oldwidth;
            matrix.postScale(scale, scale);
            iv_icon.setImageMatrix(matrix);
            iv_icon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_DOWN:
                            Log.e("sds","ACTION_DOWN");
                            mode = DRAG;
                            startPoint.set(event.getX(),event.getY());
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            Log.e("sds","ACTION_POINTER_DOWN");
                            mode = ZOOM;
                            startDis = distance(event);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.e("dsad", mode + "");
                            if (mode == DRAG) {
                                float dx = event.getX() - startPoint.x;
                                float dy = event.getY() - startPoint.y;
                                matrix.postTranslate(dx, dy);
                                startPoint.set(event.getX(),event.getY());
                                iv_icon.setImageMatrix(matrix);
                            } else if(mode==ZOOM){
                                float endDis = distance(event);
                                float mscale = endDis / startDis;
                                matrix.postScale(mscale, mscale);
                                iv_icon.setImageMatrix(matrix);
                                startDis = endDis;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.e("dw", "ACTION_UP");
                            mode = 0;
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            Log.e("dw", "ACTION_POINTER_UP");
                            mode = 0;
                            break;
                    }
                    return true;
                }
            });

        }
    }

    private static float distance(MotionEvent event){
        //两根线的距离
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx*dx + dy*dy);
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
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAward(token, id, name, price, num, (Double.parseDouble(awardOdds) / 100) + "", (Double.parseDouble(replaceOdds) / 100) + "", imgUrl), new Subscriber<Response<Award>>() {
            @Override
            public void onCompleted() {
                Log.e("aaa", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("aaa", "onError" + e.getMessage());
            }

            @Override
            public void onNext(Response<Award> arrayListResponse) {
                if (arrayListResponse.code == 0) {
                    Toast.makeText(context, "添加奖品成功", Toast.LENGTH_SHORT).show();
                    AddAwardListActivity_pt.instance.getData();
                    finish();
                }
            }
        });

    }
    void editAward(){
        HttpMethods.start(HttpMethods.getInstance().demoService.saveAward(token, id, name, price, num, (Double.parseDouble(awardOdds) / 100) + "", (Double.parseDouble(replaceOdds) / 100) + "", imgUrl, award.id), new Subscriber<Response>() {
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
                    Toast.makeText(context, "修改奖品成功", Toast.LENGTH_SHORT).show();
                    AddAwardListActivity_pt.instance.getData();
                    finish();
                }
            }
        });
    }

}
