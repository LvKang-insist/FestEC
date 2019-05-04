package com.diabin.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ec.database.DatabaseManager;
import com.diabin.latte.ec.mytable.DaoSession;
import com.diabin.latte.ec.mytable.UserProfile;

import org.hamcrest.core.Is;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C)
 *
 * @file: SignUpDelegate
 * @author: 345
 * @Time: 2019/4/21 21:21
 * @description: 注册界面
 */
public class SignUpDelegate extends LatteDelegate   {

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_passwrod)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePssword = null;
    private UserProfile userProfile;

    private ISignListener mIsignListener = null;

    /**
     * @param activity 当前的Activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //判断 当前的 activity 有没有实现这个接口
        if (activity instanceof ISignListener) {
            //向上转型
            mIsignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            //进行注册
            SignHandler.onSignUp(userProfile, mIsignListener);
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        //进入登录界面
        startWithPop(new SignInDelegate());
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePssword.getText().toString();


        boolean isPass = true;
        if (name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        //判断邮箱格式是否正确
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请至少填写 6位数的密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePssword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePssword.setError(null);
        }
        userProfile = new UserProfile(name, email, phone, password);

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }


}
