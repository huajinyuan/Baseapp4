package com.example.choujiang.cj.ac_memberget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.example.choujiang.R;
import com.example.choujiang.cj.zxing.F;
import com.example.choujiang.cj.zxing.ViewfinderView;
import com.example.choujiang.cj.zxing.camera.CameraManager;
import com.example.choujiang.cj.zxing.decode.CaptureActivityHandler;
import com.example.choujiang.cj.zxing.decode.InactivityTimer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Vector;


public class QrCodeActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    public ViewfinderView mView;
    private boolean hasSurface;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private CaptureActivityHandler handler;
    private Vector<BarcodeFormat> decodeFormats;

    TextView title;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        context = this;
        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        mView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        title = (TextView) findViewById(R.id.tv_topbar_title);
        title.setText("扫一扫");

        findViewById(R.id.iv_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return mView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        mView.drawViewfinder();
    }

    /**
     * 扫描到二维码或者条形码
     */
    public void handleDecode(Result obj, Bitmap barcode) {
        F.e("二维码：" + obj.getText());
        MemberGetActivity.instance.detailId = obj.getText();
        MemberGetActivity.instance.getData();
        finish();
    }

}
