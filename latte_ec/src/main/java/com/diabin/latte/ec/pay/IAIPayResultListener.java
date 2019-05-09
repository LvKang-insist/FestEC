package com.diabin.latte.ec.pay;

/**
 * Copyright (C)
 *
 * @file: IAIPayResultListener
 * @author: 345
 * @Time: 2019/5/7 10:08
 * @description: ${DESCRIPTION}
 */
public interface IAIPayResultListener {
    /**
     * 支付成功
     */
    void onPaySuccess();
    /**
     * 支付中
     */
    void onPaying();
    /**
     * 支付失败
     */
    void onPayFail();
    /**
     * 用户取消支付
     */
    void onPayCancel();
    /**
     * 支付时 网络出现异常
     */
    void onPayConnectError();
}
