package com.diabin.latte.deleggate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import butterknife.ButterKnife;
//import butterknife.Unbinder;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Copyright (C)
 * 文件名称: BaseDelegate
 * 创建人: 345
 * 创建时间: 2019/4/15 20:21
 * 描述: ${DESCRIPTION}
 */
public abstract class BaseDelegate extends SwipeBackFragment {

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;

    /**
     * @return 可以是一个View ，也可以是一个Layout的Id，代表一个视图
     */
    public abstract Object setLayout();


    public abstract void onBindView(@Nullable Bundle savedInstanceState,View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        //返回的是 Layout的 id
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        }
        if (rootView != null) {
            //绑定 资源（ButterKnife）
            mUnbinder = ButterKnife.bind(this,rootView);

            //子类 实现，传入 Bundle 和 碎片视图
            onBindView(savedInstanceState,rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
