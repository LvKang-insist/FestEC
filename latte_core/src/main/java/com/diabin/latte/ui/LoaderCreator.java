package com.diabin.latte.ui;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Copyright (C)
 *
 * @file: LoaderCreator
 * @author: 345
 * @Time: 2019/4/18 16:34
 * @description: ${DESCRIPTION}
 */
public final class LoaderCreator{

    private static final WeakHashMap<String,Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView creawte(String type, Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null){
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name){
        if (name == null || name.isEmpty()){
            return null;
        }
        final StringBuilder drawbleClassName = new StringBuilder();
        if (!name.contains(".")){
            final String defultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawbleClassName.append(defultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawbleClassName.append(name);
        try {
            final Class<?> drawableClass = Class.forName(drawbleClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
