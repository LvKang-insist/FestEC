package com.diabin.latte.ec.main.personal.addres;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.mapping.Converters;
import com.diabin.latte.ui.recycler.DataConverter;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;


import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C)
 *
 * @file: AddressDataConverter
 * @author: 345
 * @Time: 2019/5/11 9:52
 * @description: ${DESCRIPTION}
 */
public class AddressDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        ArrayList<MultipleItemEntity> list = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final String phone = data.getString("phone");
            final String address = data.getString("address");
            boolean isDefault = data.getBoolean("default");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(AddressItemType.ITEM_ADDRESS)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.NAME,name)
                    .setField(MultipleFields.TAG,isDefault)
                    .setField(AddresItemFileds.PHONE,phone)
                    .setField(AddresItemFileds.ADDRESS,address)
                    .build();
            list.add(entity);
        }
        return list;
    }
}
