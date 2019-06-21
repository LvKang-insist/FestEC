package com.diabin.latte.ec.main;

import android.graphics.Color;

import com.diabin.latte.deleggate.bottom.BaseBottomDelegate;
import com.diabin.latte.deleggate.bottom.BottomItemDelegate;
import com.diabin.latte.deleggate.bottom.BottomTabBean;
import com.diabin.latte.deleggate.bottom.ItemBuilder;
import com.diabin.latte.ec.main.cart.ShopCartDelegate;
import com.diabin.latte.ec.main.discover.DiscoverDelegate;
import com.diabin.latte.ec.main.index.IndexDelegate;
import com.diabin.latte.ec.main.personal.PersonalDelegate;
import com.diabin.latte.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * Copyright (C)
 *
 * @file: EcBottomDelegate
 * @author: 345
 * @Time: 2019/4/26 14:26
 * @description: ${DESCRIPTION}
 */
public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"),new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}","分类"),new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}","发现"),new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"),new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}","我的"),new PersonalDelegate());
        return builder.addItem(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
