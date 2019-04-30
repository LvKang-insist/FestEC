package com.diabin.latte.ec.main.index;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * Copyright (C)
 *
 * @file: MyScrollListener
 * @author: 345
 * @Time: 2019/4/30 14:34
 * @description: ${DESCRIPTION}
 */
public class MyScrollListener extends RecyclerView.OnScrollListener {

    private int mDy = 0;

    private final Toolbar mToolbar;

    private MyScrollListener(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
    }

    public static MyScrollListener create(Toolbar toolbar){
      return new MyScrollListener(toolbar);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //判断是 RecyclerView 是否位于顶部
        boolean flag = recyclerView.canScrollVertically(-1);
        int  toolbarHeight = mToolbar.getHeight();
        mDy += dy;
        if (!flag){
            //位于顶部 ，则设置颜色 透明
            mToolbar.setBackgroundColor(Color.TRANSPARENT);
        }
        else {
            //否则 设置渐变
            if (mDy >toolbarHeight){
                mToolbar.setBackgroundColor(Color.rgb(255,124,2));
            }else if (mDy >0&& mDy <= toolbarHeight) {
                final float scale =(float) mDy / toolbarHeight;
                final float alpha = scale * 255;
                mToolbar.setBackgroundColor(Color.argb((int)alpha,255,124,2));
            }
        }
    }
}
