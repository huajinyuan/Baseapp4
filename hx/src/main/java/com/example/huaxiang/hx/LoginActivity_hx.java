package com.example.huaxiang.hx;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.huaxiang.R;

public class LoginActivity_hx extends AppCompatActivity {
    EditText et_username, et_password;
    RadioGroup rg_login;
    String sysSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_hx);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        rg_login = (RadioGroup) findViewById(R.id.rg_login);

        rg_login.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_null) {
                    sysSign = null;
                } else if (checkedId == R.id.rb_zt) {
                    sysSign = "zt";
                } else if (checkedId == R.id.rb_tc) {
                    sysSign = "tc";
                }
            }
        });

        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_username.getText().toString().trim().equals("") || et_username.getText().toString().trim().equals("")) {

                } else {
                    Intent intent = new Intent(LoginActivity_hx.this, MainActivity_hx.class);
                    intent.putExtra("username", et_username.getText().toString().trim());
                    intent.putExtra("password", et_username.getText().toString().trim());
                    intent.putExtra("sysSign", sysSign);
                    startActivity(intent);
                }
            }
        });
//        findViewById(R.id.bt_relogin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity_hx.this, MainActivity_hx.class);
//                startActivity(intent);
//            }
//        });

    }
}
