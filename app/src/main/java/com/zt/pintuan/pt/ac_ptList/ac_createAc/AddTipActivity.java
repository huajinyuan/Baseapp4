package com.zt.pintuan.pt.ac_ptList.ac_createAc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.pintuan.R;

public class AddTipActivity extends AppCompatActivity {
    Context context;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;
    EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);
        context = this;
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("消费提示");
        tv_topbar_right.setVisibility(View.GONE);
        tv_topbar_right.setText("");
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        et_content = (EditText) findViewById(R.id.et_content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CreateAcActivity_pt.instance.activity_pt.saleRemarks = et_content.getText().toString().trim();
        CreateAcActivity_pt.instance.setData();
    }
}
