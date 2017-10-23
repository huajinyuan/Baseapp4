package com.example.choujiang.thirdpart.share;

import android.content.Intent;
import android.os.Bundle;

public abstract class AbstractComponent implements IAction {

    /**
     * 调用授权的activity的onNewIntent函数中调用该函数
     *
     * @param intent
     */
    void onNewIntent(Intent intent) {

    }

    /**
     * 调用授权的activity的onActivityResult函数中调用该函数
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    void  handleSaveInstance(Bundle savedInstanceState){}
}
