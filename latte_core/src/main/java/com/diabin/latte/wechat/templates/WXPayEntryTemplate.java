package com.diabin.latte.wechat.templates;

import com.diabin.latte.activitys.ProxyActivity;
import com.diabin.latte.deleggate.LatteDelegate;

/**
 * Copyright (C)
 *
 * @file: WXPayEntryTemplate
 * @author: 345
 * @Time: 2019/4/24 19:38
 * @description: ${DESCRIPTION}
 */
public class WXPayEntryTemplate extends ProxyActivity {
    @Override
    public LatteDelegate setRootDelegate() {
        return null;
    }

    /*
    @Override
    protected void onPaySuccess() {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPayPayFail() {
        Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);

    }

    @Override
    protected void onPayCancel() {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }*/
}
