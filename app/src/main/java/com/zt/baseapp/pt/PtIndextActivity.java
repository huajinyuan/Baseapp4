package com.zt.baseapp.pt;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.zt.baseapp.R;
import com.zt.baseapp.model.Response;
import com.zt.baseapp.module.base.BaseActivity;
import com.zt.baseapp.network.ResponseHandle;
import com.zt.baseapp.network.retrofit.HttpMethods;
import com.zt.baseapp.pt.memberget.MemberGetActivity;
import com.zt.baseapp.pt.model.Logd;
import com.zt.baseapp.pt.ptbb.BbActivityActivity;
import com.zt.baseapp.pt.ptbb.MemberListActivity;
import com.zt.baseapp.pt.ptbb.PdListActivity;
import com.zt.baseapp.pt.ptsetting.AcListActivity;
import com.zt.baseapp.utils.AppContext;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

@RequiresPresenter(PtIndextPresenter.class)
public class PtIndextActivity extends BaseActivity<PtIndextPresenter> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        AppContext.getInstance().init(this);
//        ResponseHandle.IRetryListener listener = new ResponseHandle.IRetryListener() {
//            @Override
//            public Func1<Observable<? extends Throwable>, Observable<?>> retry() {
//                Log.e("=========","==============");
//                return null;
//            }
//        };
//        ResponseHandle.setRetryListener(listener);
//        unbinder = ButterKnife.bind(this);
        findView(R.id.img_topbar_back).setOnClickListener(view -> finishActivity());
        findView(R.id.tv_action_aclist).setOnClickListener(view -> click2Intent(view));
        findView(R.id.tv_action_ptlist).setOnClickListener(view -> click2Intent(view));
        findView(R.id.tv_action_memberlist).setOnClickListener(view -> click2Intent(view));
        findView(R.id.tv_action_pt_ac).setOnClickListener(view -> click2Intent(view));
        findView(R.id.tv_action_memberget).setOnClickListener(view -> click2Intent(view));


        HttpMethods.getInstance().login2("shanghu2", "123456").subscribe(new Observer<Response<Logd>>() {

            @Override
            public void onCompleted() {
                Log.e("=============", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("=============", e.toString() + "");
            }

            @Override
            public void onNext(Response<Logd> loginDataResponse) {

                if (loginDataResponse.code==0){

                    Log.e("=============", loginDataResponse.data.getToken());
                }else {
                    Log.e("=============", loginDataResponse.msg);
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    public void finishActivity() {

        this.finish();
    }

    public void click2Intent(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_action_aclist:
                intent = new Intent(this, BbActivityActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_action_ptlist:
                intent = new Intent(this, PdListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_action_memberlist:
                intent = new Intent(this, MemberListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_action_pt_ac:
                intent = new Intent(this, AcListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_action_memberget:
                intent = new Intent(this, MemberGetActivity.class);
                startActivity(intent);
                break;

        }
    }



}
