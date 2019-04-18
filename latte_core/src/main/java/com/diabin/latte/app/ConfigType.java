package com.diabin.latte.app;

/**
 * Copyright (C)
 * 文件名称: ConfigType
 * 创建人: 345
 * 创建时间: 2019/4/14 18:39
 * 描述: 在整个应用程序中 是一个单例，并且只能被初始化一次。
 */
public enum ConfigType {

    API_HOST, //网络请求
    APPLICATION_CONTEXT,//全局的上下文
    CONFIG_READY, // 初始化或者配置 完成了没有
    ICON    //字体的初始化
}
