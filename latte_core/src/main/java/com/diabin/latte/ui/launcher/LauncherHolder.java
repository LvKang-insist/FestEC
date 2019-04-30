package com.diabin.latte.ui.launcher;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.diabin.latte.R;
import com.diabin.latte.app.Latte;

/**
 * Copyright (C)
 *
 * @file: LauncherHolder
 * @author: 345
 * @Time: 2019/4/21 15:15
 * @description: ${DESCRIPTION}
 */
public class LauncherHolder extends Holder<Integer> {

    private AppCompatImageView mImageView ;

    public LauncherHolder(View itemView) {
        super(itemView);
    }

    /**
     * 加载布局
     */
    @Override
    protected void initView(View itemView) {
        mImageView = itemView.findViewById(R.id.launcher_image);
    }
    /**
     * 绑定数据
     */
    @Override
    public void updateUI(Integer data) {
        mImageView.setBackgroundResource(data);
    }
}
