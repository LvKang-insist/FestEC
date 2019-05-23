package com.diabin.latte.ec.main.index.search;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.diabin.latte.app.Latte;
import com.diabin.latte.ec.R;
import com.diabin.latte.util.storage.LattePreference;

/**
 * Copyright (C)
 *
 * @file: SearchItemClickListener
 * @author: 345
 * @Time: 2019/5/13 19:46
 * @description: ${DESCRIPTION}
 */
public class SearchItemClickListener extends SimpleClickListener {
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if ((adapter.getData().size()-1) == position){

        }
    }
    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
