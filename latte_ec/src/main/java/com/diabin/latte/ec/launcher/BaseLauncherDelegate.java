package com.diabin.latte.ec.launcher;

import android.app.Activity;

import com.diabin.latte.app.AccountManager;
import com.diabin.latte.app.IUserChecker;
import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ui.loader.ILauncherListener;
import com.diabin.latte.ui.loader.OnLauncherFinishTag;

/**
 * Copyright (C)
 *
 * @file: BaseLauncherDelegate
 * @author: 345
 * @Time: 2019/4/23 20:20
 * @description: 抽象类，用于管理 首页倒计时和轮播图
 */
public abstract class BaseLauncherDelegate extends LatteDelegate {
    private ILauncherListener mILauncherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    public void checkSignIn(){
        //检查用户是否登录了 APP
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
                //不等于 null 说明接口Activity实现了
                if (mILauncherListener != null) {
                    //已经登录
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }
            }
            @Override
            public void onNoSignIn() {
                if (mILauncherListener != null) {
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            }
        });
    }
}
