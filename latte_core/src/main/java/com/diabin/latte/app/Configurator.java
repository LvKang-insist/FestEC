package com.diabin.latte.app;

import android.util.Log;
import android.view.inputmethod.InputContentInfo;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Copyright (C)
 * 文件名称: Configurator
 * 创建人: 345
 * 创建时间: 2019/4/14 18:37
 * 描述:  Configurator : 配置程序
 */
public class Configurator {
    //配置信息
    private static final HashMap<String, Object> LATTE_CONFIGS = new HashMap<>();
    //存放 字体图标
    private  static final ArrayList<IconFontDescriptor> ICONS  = new ArrayList<>();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    /**
     * 静态内部类的 单例模式
     */
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * @return 返回单例对象
     */
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * @return 返回配置信息
     */
    final HashMap<String, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }
    /**
     * 配置成功
     */
    public final void configure() {
        initIcons();//初始化 字体图片
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    /**
     * @param host 网络地址
     * @return 返回当前配置对象
     */
    public final Configurator WithApiHost(String host){
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(),host);

        return this;
    }

    /**
     * 初始化 字体图片
     */
    private void initIcons(){
        if(ICONS.size() >0){
            //拿出第一个
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            //从第一个开始 往后拿
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    /**
     * @param descriptor 字体文件
     * @return 返回当前对象
     */
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    /**
     * 获取配置，如果没有调用 configure() ,则没有配置成功，然后抛出异常
     */
    private void checkConfiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady){
            throw new RuntimeException("Configuration is not ready ,call configure");
        }
    }

    /**
     *
     * @param key 枚举对象，代表要查询的某项配置
     * @param <T> 调用的类型
     * @return 返回配置的信息。
     */
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration(); //判断配置是否成功
        return (T) LATTE_CONFIGS.get(key.name());
    }
}
