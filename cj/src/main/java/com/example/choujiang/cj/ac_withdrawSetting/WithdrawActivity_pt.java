package com.example.choujiang.cj.ac_withdrawSetting;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choujiang.R;
import com.example.choujiang.cj.ac_staffSend.m.Staff_cj;
import com.example.choujiang.model.Response;
import com.example.choujiang.module.base.BaseActivity;
import com.example.choujiang.network.retrofit.HttpMethods;
import com.example.choujiang.utils.ACache;
import com.example.choujiang.utils.ACacheKey;

import nucleus.factory.RequiresPresenter;
import rx.Subscriber;

@RequiresPresenter(WithdrawPresenter_pt.class)
public class WithdrawActivity_pt extends BaseActivity<WithdrawPresenter_pt> {
    Context context;
    ACache aCache;
    public String token;
    TextView tv_topbar_title;
    TextView tv_topbar_right;
    ImageView iv_topbar_right;

    TextView tv_name;
    TextView tv_balance;
    EditText et_money;

    Staff_cj staff_cj;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw_pt;
    }

    @Override
    protected void initView() {
        context = this;
        aCache = ACache.get(context);
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_right = (TextView) findViewById(R.id.tv_topbar_right);
        iv_topbar_right = (ImageView) findViewById(R.id.iv_topbar_right);
        tv_topbar_title.setText("提现");
        tv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setVisibility(View.GONE);
        iv_topbar_right.setImageResource(R.mipmap.icon_top_right_pt);

        tv_name = findView(R.id.tv_name);
        tv_balance = findView(R.id.tv_balance);
        et_money = findView(R.id.et_money);
    }


    @Override
    protected void initData() {
        token = aCache.getAsString(ACacheKey.TOKEN);
        staff_cj = (Staff_cj) getIntent().getSerializableExtra("staff");
        if (staff_cj != null) {
            tv_name.setText(staff_cj.name);
            tv_balance.setText("￥" + staff_cj.balance);
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
        findViewById(R.id.bt_withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = et_money.getText().toString().trim();
                if (staff_cj != null) {
                    if (!money.isEmpty()) {
                        money = money.equals(".") ? ".0" : money;
                        if (Double.parseDouble(money) > staff_cj.balance) {
                            Toast.makeText(context, "不能大于余额", Toast.LENGTH_SHORT).show();
                        } else if (Double.parseDouble(money) == 0) {
                            Toast.makeText(context, "不能为0", Toast.LENGTH_SHORT).show();
                        } else {
                            getData();
                        }
                    }
                } else {
                    Toast.makeText(context, "未获取到员工信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void getData(){
        HttpMethods.start(HttpMethods.getInstance().demoService.accountWithDraw(token, staff_cj.id,et_money.getText().toString().trim()), new Subscriber<Response>() {
            @Override
            public void onStart() {
                super.onStart();
            }

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
                    finish();
                    Toast.makeText(context, "提现成功", Toast.LENGTH_SHORT).show();
                    AccountDetailActivity_pt.instance.refresh();
                } else {
                    Toast.makeText(context, arrayListResponse.msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
