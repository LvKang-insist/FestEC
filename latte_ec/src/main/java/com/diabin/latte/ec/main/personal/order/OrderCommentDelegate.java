package com.diabin.latte.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ui.widget.AutoPhotoLayout;
import com.diabin.latte.ui.widget.StarLayout;
import com.diabin.latte.util.callback.CallBackType;
import com.diabin.latte.util.callback.CallbackManager;
import com.diabin.latte.util.callback.IGlobalCallback;
import com.diabin.latte.util.dimen.SetToolBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C)
 *
 * @file: OrderCommentDelegate
 * @author: 345
 * @Time: 2019/5/12 11:07
 * @description: 评价晒单
 */
public class OrderCommentDelegate extends LatteDelegate {

    @BindView(R2.id.tb_shop_cart)
    Toolbar mToolbar = null;
    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout = null;
    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;

    @OnClick(R2.id.top_tv_comment_commit)
    void onCLickSubmit(){
        Toast.makeText(getContext(), mStarLayout.getStarCount()+" ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);

        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance().addCallback(CallBackType.ON_CROP, new IGlobalCallback<Uri>() {
            @Override
            public void executeCallBack(Uri args) {
                Toast.makeText(getContext(), args.getPath(), Toast.LENGTH_SHORT).show();
                mAutoPhotoLayout.onCropTarget(args);
            }
        });
    }
}
