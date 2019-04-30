package com.diabin.latte.deleggate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diabin.latte.activitys.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Copyright (C)
 * 文件名称: BaseDelegate
 * 创建人: 345
 * 创建时间: 2019/4/15 20:21
 * 描述: Fragment
 */
public abstract class BaseDelegate extends SwipeBackFragment {

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;

    /**
     * @return 可以是一个View ，也可以是一个Layout的Id，代表一个视图
     */
    public abstract Object setLayout();


    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        //返回的是 Layout的 id
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("setLayout() type must be int or view");
        }
        //绑定 资源（ButterKnife）
        mUnbinder = ButterKnife.bind(this, rootView);
        //子类 实现，传入 Bundle 和 碎片视图
        onBindView(savedInstanceState, rootView);

        return rootView;
    }

    /**
     * @return 返回一个 Activity
     */
    public final ProxyActivity getProxyActivity(){
        return (ProxyActivity) _mActivity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
