package com.example.choujiang.thirdpart.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.choujiang.base.ReturnCode;
import com.example.choujiang.model.Response;
import com.example.choujiang.thirdpart.ConstantParams;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_AUTH_DENIED;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_OK;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_USER_CANCEL;

public abstract class WeChatActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI mWXApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWXApi = WXAPIFactory.createWXAPI(this, ConstantParams.WX_APP_ID, true);
        mWXApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWXApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            Response response;
            switch (baseResp.errCode) {
                case ERR_OK:
                    response = new Response<>(ReturnCode.CODE_SUCCESS, "分享成功");
                    break;
                case ERR_USER_CANCEL:
                    response = new Response(ReturnCode.CODE_ERROR, "取消分享");
                    break;
                case ERR_AUTH_DENIED:
                    response = new Response(ReturnCode.CODE_ERROR, "未通过审核");
                    break;
                default:
                    response = new Response(ReturnCode.LOCAL_UNKNOWN_ERROR, "未知错误");
                    break;
            }
            EventBus.getDefault().post(response);
            finish();
        }
    }
}
