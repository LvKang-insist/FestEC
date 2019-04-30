package com.diabin.latte.wechat.templates;

import com.diabin.latte.activitys.ProxyActivity;
import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.wechat.BaseWXEntryActivity;
import com.diabin.latte.wechat.LatteWeChat;

/**
 * Copyright (C)
 *
 * @file: WXEntryTemplate
 * @author: 345
 * @Time: 2019/4/24 19:37
 * @description: ${DESCRIPTION}
 */
public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
