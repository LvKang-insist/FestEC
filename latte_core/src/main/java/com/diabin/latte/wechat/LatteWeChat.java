package com.diabin.latte.wechat;

import android.app.Activity;

import com.diabin.latte.app.ConfigType;
import com.diabin.latte.app.Latte;
import com.diabin.latte.wechat.callbacks.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Copyright (C)
 *
 * @file: LatteWeChat
 * @author: 345
 * @Time: 2019/4/24 21:36
 * @description: ${DESCRIPTION}
 */
public class LatteWeChat {
    public static final String App_ID = Latte.getConfiguration(ConfigType.WE_CHAT_APP_ID);
    public static final String APP_SECRET = Latte.getConfiguration(ConfigType.WE_CHAT_APP_SECRET);

    private IWeChatSignInCallback mSignInCallback = null;
    /**
     * 要进行微信的登录和支付都需要 IWXAPI 接口
     */
    private final IWXAPI WXAPI;

    private static final class holder {
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static LatteWeChat getInstance() {
        return holder.INSTANCE;
    }

    private LatteWeChat() {
        final Activity activity = Latte.getConfiguration(ConfigType.ACTIVITY);

        //校验
        WXAPI = WXAPIFactory.createWXAPI(activity, App_ID, true);
        WXAPI.registerApp(App_ID);
    }

    /**
     * @return 返回微信 API
     */
    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public LatteWeChat onSignSuccess(IWeChatSignInCallback callback){
        this.mSignInCallback = callback;
        return this;
    }

    public IWeChatSignInCallback getSignInCallback() {
        return mSignInCallback;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }

}
