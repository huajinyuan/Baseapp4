package com.example.choujiang.cj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.choujiang.R;


public class LoginActivity_cj extends AppCompatActivity {
    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cj);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_username.getText().toString().trim().equals("") || et_username.getText().toString().trim().equals("")) {

                } else {
                    Intent intent = new Intent(LoginActivity_cj.this, MainActivity_cj.class);
                    intent.putExtra("username", et_username.getText().toString().trim());
                    intent.putExtra("password", et_username.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.bt_relogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity_cj.this, MainActivity_cj.class);
                startActivity(intent);
            }
        });

    }
}
