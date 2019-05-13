package com.admin.festec.example.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.admin.festec.example.ExampleActivity;
import com.alibaba.fastjson.JSONObject;
import com.diabin.latte.app.Latte;


import java.util.Set;

import cn.jpush.android.api.JPluginPlatformInterface;
import cn.jpush.android.api.JPushInterface;

/**
 * Copyright (C)
 *
 * @file: PushReceiver
 * @author: 345
 * @Time: 2019/5/11 17:41
 * @description: ${DESCRIPTION}
 */
public class PushReceiver extends BroadcastReceiver {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        if (bundle.keySet() != null) {
            final Set<String> keys = bundle.keySet();
            JSONObject json = new JSONObject();
            for (String key : keys) {
                final Object val = bundle.get(key);
                json.put(key, val);
            }
        }
        final String pushAction = intent.getAction();
        if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)){
            //处理接收到的消息
            onReceivedMessage(bundle);
        }else if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_OPENED)){
            //打开相应的 notification
            onOpenNotification(context,bundle);
        }
    }

    /**
     * 拿到对应的 信息
     */
    private void onReceivedMessage(Bundle bundle){
        final String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        final String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        final int notificationId  = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        final String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
    }

    private void onOpenNotification(Context context,Bundle bundle){
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final Bundle openActivityBundle = new Bundle();
        //点击 通知的时候 打开程序
        final Intent intent = new Intent(context,ExampleActivity.class);
        intent.putExtras(openActivityBundle);
        //另启了一个Activity ，清除了上面的Activity。
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //打开一个Activity
        ContextCompat.startActivity(context,intent,null);
    }
}
