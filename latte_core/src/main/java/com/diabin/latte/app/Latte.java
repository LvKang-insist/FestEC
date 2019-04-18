package com.diabin.latte.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Copyright (C)
 * 文件名称: Latte
 * 创建人: 345
 * 创建时间: 2019/4/14 18:34
 * 描述: 初始化
 */
public final class Latte {

    /**
     * @param context context
     * @return 返回配置对象
     */
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context);
        return Configurator.getInstance();
    }

    /**
     * @return 返回 配置信息
     */
    public static HashMap<String ,Object> getConfigurations(){
        return Configurator.getInstance().getLatteConfigs();
    }

    /**
     * @return 返回 Context
     */
    public static Context  getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
