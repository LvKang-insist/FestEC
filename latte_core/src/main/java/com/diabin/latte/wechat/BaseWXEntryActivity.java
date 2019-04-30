package com.diabin.latte.wechat;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diabin.latte.net.RestClient;
import com.diabin.latte.net.callback.IError;
import com.diabin.latte.net.callback.IFailure;
import com.diabin.latte.net.callback.ISuccess;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Copyright (C)
 *
 * @file: BaseWXEntryActivity
 * @author: 345
 * @Time: 2019/4/25 12:58
 * @description: ${DESCRIPTION}
 */
public abstract class BaseWXEntryActivity  extends BaseWXActivity{
    /**
     * 用户登录成功后回调
     */
   protected abstract void onSignInSuccess(String userInfo);

    /**
     *  微信发送请求到第三方应用后的回调
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 第三方应用发送请求到微信后的回调
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
        //拿到 code
        final String code = ((SendAuth.Resp)baseResp).code;

        final StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.App_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        Log.e("authUrl", authUrl.toString() );

        getAuth(authUrl.toString());
    }

    protected  void getAuth(final String authUrl){
        RestClient
                .builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void OnSuccess(String response) {
                       final JSONObject authObj = JSON.parseObject(response);
                       final String accessToKen = authObj.getString("access_token");
                       final String openId= authObj.getString("openid");

                       final StringBuilder userInfoUrl = new StringBuilder();
                       userInfoUrl
                               .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                               .append(accessToKen)
                               .append("&openid=")
                               .append(openId)
                               .append("&lang=")
                               .append("zh_CN");
                        Log.e("userInfoUrl", userInfoUrl.toString() );
                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String userInfoUrl){
        RestClient
                .builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void OnSuccess(String response) {
                        onSignInSuccess(response);
                    }
                })
                .ifailure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .ierror(new IError() {
                    @Override
                    public void OnError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
