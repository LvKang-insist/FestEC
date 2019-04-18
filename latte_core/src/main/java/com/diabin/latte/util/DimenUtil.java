package com.diabin.latte.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.diabin.latte.app.Latte;

/**
 * Copyright (C)
 *
 * @file: DimenUtil
 * @author: 345
 * @Time: 2019/4/18 16:57
 * @description: ${DESCRIPTION}
 */
public class DimenUtil {
    /**
     *
     * @return 返回屏幕的宽
     */
    public static int getScreenWidth(){
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * @return 返回屏幕的高
     */
    public static int getScreenHeight(){
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }


}
