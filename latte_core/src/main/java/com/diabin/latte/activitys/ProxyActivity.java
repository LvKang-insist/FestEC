package com.diabin.latte.activitys;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.diabin.latte.R;
import com.diabin.latte.deleggate.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Copyright (C)
 * 文件名称: ProxyActivity
 * 创建人: 345
 * 创建时间: 2019/4/15 20:18
 * 描述:  Activity 继承自 SupportActivity
 */
//Fragmentation 提供的 SupportActivity
public abstract class ProxyActivity extends SupportActivity {

    /**
     * @return 返回根 Delegate
     */
    public abstract LatteDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    /**
     * 初始化 视图
     * @param savedInstanceState 保存的数据
     */
    private void initContainer(@Nullable Bundle savedInstanceState) {
        @SuppressLint("RestrictedApi") final ContentFrameLayout container = new ContentFrameLayout(this);
        //设置这个视图的Id
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null){
            //SupportActivity 独有的一个方法
            //加载根Fragment 即Activity的第一个Fragment ，或者Fragment内的第一个Fragment
            //即 加载碎片
            loadRootFragment(R.id.delegate_container,setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
