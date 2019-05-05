package com.admin.festec.example.event;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebView;
import android.widget.Toast;

import com.diabin.latte.deleggate.web.event.Event;

/**
 * Copyright (C)
 *
 * @file: TestEvent
 * @author: 345
 * @Time: 2019/5/5 8:47
 * @description: ${DESCRIPTION}
 */
public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_SHORT).show();
        if ("test".equals(getAction())){
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall();",null);
                }
            });
        }
        return null;
    }
}
