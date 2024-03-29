package com.diabin.latte.ec.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.diabin.latte.ui.loader.LatteLoader;

/**
 * Copyright (C)
 *
 * @file: PayAsyncTask
 * @author: 345
 * @Time: 2019/5/7 11:25
 * @description: ${DESCRIPTION}
 */
public class PayAsyncTask extends AsyncTask<String, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private final Activity ACTIVITY;
    private final IAIPayResultListener LISTENER;


    /**
     * 订单支付成功
     */
    private static final String AL_PAY_STATUS_SUCCESS = "9000";
    /**
     * 订单处理中
     */
    private static final String AL_PAY_STATUS_PAYING = "8000";

    /**
     * 订单支付失败
     */
    private static final String AL_PAY_STATUS_FAIL = "4000";

    /**
     * 用户取消
     */
    private static final String AL_PAY_STATUS_CANCEL = "6001";
    /**
     * 支付网络错误
     */
    private static final String AL_PAY_STATUS_CONNECT_ERROR = "6002";


    public PayAsyncTask(Activity activity, IAIPayResultListener listener) {
        this.ACTIVITY = activity;
        this.LISTENER = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        final String alPaySign = strings[0];

//        final PayTask payTask = new PayTask(ACTIVITY);
//
//        return payTask.pay(alPaySign, true);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        LatteLoader.stopLoading();
        final PayResult payResult = new PayResult(s);
        //支付宝返回此次支付结构及加签，建议对支付宝签名信息拿签约是支付宝提供的公钥做验签
        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();
        Log.e("AL_PAY_RESULT", resultInfo);
        Log.e("AL_PAY_RESULT", resultStatus);

        switch (resultStatus) {
            case AL_PAY_STATUS_SUCCESS:
                LISTENER.onPaySuccess();
                break;
            case AL_PAY_STATUS_FAIL:
                LISTENER.onPayFail();
                break;
            case AL_PAY_STATUS_PAYING:
                LISTENER.onPaying();
                break;
            case AL_PAY_STATUS_CANCEL:
                LISTENER.onPayCancel();
                break;
            case AL_PAY_STATUS_CONNECT_ERROR:
                LISTENER.onPayConnectError();
                break;
            default:
        }
    }
}
