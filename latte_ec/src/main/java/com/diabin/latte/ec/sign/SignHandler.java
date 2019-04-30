package com.diabin.latte.ec.sign;

import android.widget.Toast;

import com.diabin.latte.app.AccountManager;
import com.diabin.latte.app.Latte;
import com.diabin.latte.ec.database.DatabaseManager;
import com.diabin.latte.ec.mytable.DaoSession;
import com.diabin.latte.ec.mytable.UserProfile;
import com.diabin.latte.ec.mytable.UserProfileDao;
import java.util.List;


/**
 * Copyright (C)
 *
 * @file: SignHandler
 * @author: 345
 * @Time: 2019/4/22 19:39
 * @description: ${DESCRIPTION}
 */
public class SignHandler {

    private static UserProfileDao getDao(){
        DaoSession dao = DatabaseManager.getInstance().getDao();
        return dao.getUserProfileDao();
    }

    /**
     * @param userProfile 用户注册的数据
     * @param signListener
     */
    public static void onSignUp(UserProfile userProfile,ISignListener signListener){
        getDao().insertOrReplace(userProfile);

        // 注册成功 回调到activity中
        signListener.onSignUpSuccess();
    }

    /**
     * @param userProfile 用户登录的数据
     * @param signListener
     */
    public static void onSignIn(UserProfile userProfile,ISignListener signListener){
        List<UserProfile> userProfiles = getDao().loadAll();
        //测试用
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
        for(UserProfile up : userProfiles){

            if(up.getEmail().equals(userProfile.getEmail())){
                if (up.getPassword().equals(userProfile.getPassword())){
                    //已经登录成功了
                    AccountManager.setSignState(true);
                    //回调到activity中，登录成功
                    signListener.onSignInSuccess();
                }else {
                    Toast.makeText(Latte.getApplication(), "密码输入错误", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(Latte.getApplication(), "该邮箱还未注册，请前往注册", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
