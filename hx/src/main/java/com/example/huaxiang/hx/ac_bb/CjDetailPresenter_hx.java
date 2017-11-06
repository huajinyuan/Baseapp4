package com.example.huaxiang.hx.ac_bb;

import android.os.Bundle;
import android.util.Log;

import com.example.huaxiang.di.BaseAppManager;
import com.example.huaxiang.module.base.BasePresenter;

/**
 * Created by caiyk on 2017/9/28.
 */

public class CjDetailPresenter_hx extends BasePresenter<CjDetailActivity_hx> {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        BaseAppManager.getInstance().inject(this);
        Log.e("aaa", "Presenter onCreate");
    }

//    void login(){
//        HttpMethods.getInstance().login("shanghu2", "123456").subscribe(new Subscriber<Response<LoginData_pt>>(){
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                Log.e("=============", "onStart");
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.e("=============", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("=======onError", e.toString() + "");
//            }
//
//            @Override
//            public void onNext(Response<LoginData_pt> logdResponse) {
//                if (logdResponse.code==0){
//                    getView().aCache.put(ACacheKey.TOKEN, logdResponse.data.getToken());
//                    getView().token = logdResponse.data.getToken();
//                    Log.e("aaa========Token:", getView().token);
//                    getReport(0);
//
//                }else {
//                    Log.e("=======onNext", logdResponse.msg);
//                }
//            }
//        });
//    }
//    void getReport(int status){
//        HttpMethods.getInstance().getReport(getView().token, status).subscribe(new Subscriber<Response<Report_cj>>() {
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                Log.e("aaa", "onStart");
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.e("aaa", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("aaa======onError", e.toString() + "");
//            }
//
//            @Override
//            public void onNext(Response<Report_cj> response) {
//                if (response.code == 0) {
//                    getView().setReport(response.data);
//                    Log.e("aaa======onNext", response.data.toString());
//                } else {
//                    Log.e("aaa======onNext", response.msg);
//                }
//            }
//        });
//    }

}
