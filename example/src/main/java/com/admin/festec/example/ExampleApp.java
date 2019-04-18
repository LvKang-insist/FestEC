package com.admin.festec.example;

import android.app.Application;

import com.diabin.latte.app.Latte;
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
                .WithApiHost("https://www.cnblogs.com/peitong/p/")
                .withIcon(new FontAwesomeModule())
//                .withIcon(new FontEcModule())
                .configure();

    }
}
