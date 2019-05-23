package com.diabin.latte.ec.main.index.search;

import com.alibaba.fastjson.JSONArray;
import com.diabin.latte.ui.recycler.DataConverter;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * Copyright (C)
 *
 * @file: SearchDataConverter
 * @author: 345
 * @Time: 2019/5/13 15:34
 * @description: ${DESCRIPTION}
 */
public class SearchDataConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        //获取历史记录
        final String jsonStr = LattePreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if (!"".equals(jsonStr)) {
            final JSONArray array = JSONArray.parseArray(jsonStr);
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                final String hostoryItemText = array.getString(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT, hostoryItemText)
                        .build();
                ENTITLES.add(entity);
            }
        }
        return ENTITLES;
    }
}
