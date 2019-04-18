package com.diabin.latte.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.diabin.latte.R;
import com.diabin.latte.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Copyright (C)
 *
 * @file: LatteLoader
 * @author: 345
 * @Time: 2019/4/18 16:47
 * @description: ${DESCRIPTION}
 */
public class LatteLoader {

    private static final int LOADER_SIZE_SCALE = 8;

    private static final int LOADER_OFFSET_SCALT = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    private static final String DEFULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    public static void showLoading(Context context,String type){
        final AppCompatDialog dialog = new AppCompatDialog(context,R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.creawte(type,context);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth/LOADER_SIZE_SCALE;
            lp.height = deviceHight/LOADER_SIZE_SCALE;
            lp.height = lp.height+deviceHight/ LOADER_OFFSET_SCALT;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
         showLoading(context,DEFULT_LOADER);
    }
    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADERS){
            if (dialog.isShowing()){
                dialog.cancel();
            }
        }
    }
}
