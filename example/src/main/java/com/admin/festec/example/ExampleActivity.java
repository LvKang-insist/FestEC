package com.admin.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.diabin.latte.activitys.ProxyActivity;
import com.diabin.latte.app.Latte;
import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.launcher.LauncherDelegate;
import com.diabin.latte.ec.main.EcBottomDelegate;
import com.diabin.latte.ec.sign.ISignListener;
import com.diabin.latte.ui.loader.ILauncherListener;
import com.diabin.latte.ui.loader.OnLauncherFinishTag;
import com.diabin.latte.util.callback.CallBackType;
import com.diabin.latte.util.callback.CallbackManager;
import com.diabin.latte.util.callback.IGlobalCallback;

import cn.jpush.android.api.JPushInterface;
import qiu.niorgai.StatusBarCompat;


public class ExampleActivity extends ProxyActivity implements
        ISignListener, ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Latte.getConfigurator().withActivity(this);

        CallbackManager.getInstance()
                .addCallback(CallBackType.PUSH, new IGlobalCallback<Boolean>(){
                    @Override
                    public void executeCallBack(Boolean args) {
                        //打开极光推送
                        if (args){
                            Toast.makeText(ExampleActivity.this, "推送打开", Toast.LENGTH_SHORT).show();
                            //判断是 推送是否停止
                            if (JPushInterface.isPushStopped(ExampleActivity.this)) {
                                JPushInterface.resumePush(ExampleActivity.this);
                            }
                            //关闭极光推送
                        }else {
                            Toast.makeText(ExampleActivity.this, "推送关闭", Toast.LENGTH_SHORT).show();
                            if (!JPushInterface.isPushStopped(ExampleActivity.this)){
                                JPushInterface.stopPush(ExampleActivity.this);
                            }
                        }
                    }
                } );

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
        return new LauncherDelegate();
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
                getSupportDelegate().startWithPop(new EcBottomDelegate());
//                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            case NOT_SIGNED:
                break;
            default:
                break;
        }
    }
}
