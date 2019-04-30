package com.diabin.latte.ui.banner;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.diabin.latte.R;
import com.diabin.latte.app.Latte;

/**
 * Copyright (C)
 *
 * @file: ImageHolder
 * @author: 345
 * @Time: 2019/4/27 19:42
 * @description: ${DESCRIPTION}
 */
public class ImageHolder extends Holder<String> {

    private AppCompatImageView mImageView ;

    public ImageHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        mImageView = itemView.findViewById(R.id.index_photo);
    }

    @Override
    public void updateUI(String data) {
        Glide.with(Latte.getApplication())
                .load(data)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
//                .fitCenter()
                .into(mImageView);
    }
}
