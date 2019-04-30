package com.admin.festec.example;

import android.app.Application;

import com.diabin.latte.app.Latte;
import com.diabin.latte.ec.database.DatabaseManager;
import com.diabin.latte.ec.icon.FontEcModule;
import com.diabin.latte.net.interceptors.DebugInterceptor;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Copyright (C)
 * 文件名称: ExampleApp
 * 创建人: 345
 * 创建时间: 2019/4/14 20:29
 * 描述: 继承自 Application ，用于进行初始化
 */
public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Latte.init(this)
                .WithApiHost("http://47.106.101.44:8080/data/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
//                .withInterceptor(new DebugInterceptor("",R.raw.test))
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .configure();
        initStetho();
        DatabaseManager.getInstance().init(this);
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                       .build());
    }
}
