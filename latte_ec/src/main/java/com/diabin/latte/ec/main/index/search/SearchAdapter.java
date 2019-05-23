package com.diabin.latte.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.diabin.latte.ec.R;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.ui.recycler.MultipleRecyclerAdapter;
import com.diabin.latte.ui.recycler.MultipleViewHolder;
import com.diabin.latte.util.storage.LattePreference;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Copyright (C)
 *
 * @file: SearchAdapter
 * @author: 345
 * @Time: 2019/5/13 15:42
 * @description: ${DESCRIPTION}
 */
public class SearchAdapter extends MultipleRecyclerAdapter {
    protected SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);

    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);

        switch (holder.getItemViewType()) {
            case SearchItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = holder.getView(R.id.tv_search_item);
                final String history = entity.getField(MultipleFields.TEXT);
                tvSearchItem.setText(history);
                break;
            default:
                break;
        }
    }
}
