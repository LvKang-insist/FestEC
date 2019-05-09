package com.diabin.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Copyright (C)
 *
 * @file: GoodsDetailDelegate
 * @author: 345
 * @Time: 2019/4/29 13:31
 * @description: 商品详情
 */
public class GoodsDetailDelegate extends LatteDelegate {

    public static GoodsDetailDelegate create(){
       return new GoodsDetailDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }
}
