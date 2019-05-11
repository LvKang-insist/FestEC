package com.admin.festec.example;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;
import com.diabin.latte.activitys.ProxyActivity;
import com.diabin.latte.app.Latte;
import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.launcher.LauncherDelegate;
import com.diabin.latte.ec.main.EcBottomDelegate;
import com.diabin.latte.ec.sign.ISignListener;
import com.diabin.latte.ec.sign.SignInDelegate;
import com.diabin.latte.ui.loader.ILauncherListener;
import com.diabin.latte.ui.loader.OnLauncherFinishTag;

import cn.jpush.android.api.JPushInterface;
import me.yokeyword.fragmentation.ISupportFragment;
import qiu.niorgai.StatusBarCompat;


public class ExampleActivity extends ProxyActivity implements
        ISignListener, ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Latte.getConfigurator().withActivity(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }
        /*
         * 一句话 实现沉浸式状态栏,
         */
        StatusBarCompat.translucentStatusBar(this,true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        //程序启动，进入倒计时页面
        return new EcBottomDelegate();
    }

    @Override
    public void onSignInSuccess() {
        getSupportDelegate().start(new EcBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功,请点击下方按钮进行登录", Toast.LENGTH_SHORT).show();
    }


    /**
     * 程序启动页 的回调
     * @param onLauncherFinishTag 是否登录
     */
    @Override
    public void onLauncherFinish(OnLauncherFinishTag onLauncherFinishTag) {
        switch (onLauncherFinishTag) {
            case SIGNED:
                getSupportDelegate().start(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                getSupportDelegate().start(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
