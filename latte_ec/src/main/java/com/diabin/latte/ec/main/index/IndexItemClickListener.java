package com.diabin.latte.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.detail.GoodsDetailDelegate;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;

/**
 * Copyright (C)
 *
 * @file: IndexItemClickListener
 * @author: 345
 * @Time: 2019/4/29 13:09
 * @description: indexDelegate 中RecyclerView 的点击事件
 */
public class IndexItemClickListener extends SimpleClickListener {
    private final LatteDelegate DELEGATE;

    private IndexItemClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(LatteDelegate delegate){
        return new IndexItemClickListener(delegate);
    }
    /**
     * 点击事件
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodsId = entity.getField(MultipleFields.ID);
        final GoodsDetailDelegate delegate= GoodsDetailDelegate.create(goodsId);
        DELEGATE.getParentDelegate().getSupportDelegate().start(delegate);
    }
    /**
     * 长点击
     */
    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
    /**
     * 子事件点击
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
    /**
     * 子事件长点击
     */
    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
