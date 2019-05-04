package com.diabin.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.deleggate.bottom.BottomItemDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.main.sort.content.ContentDelegate;
import com.diabin.latte.ec.main.sort.list.VerticalListDelegate;

/**
 * Copyright (C)
 *
 * @file: SortDelegate
 * @author: 345
 * @Time: 2019/4/26 14:36
 * @description: 分类页面：共三个delegate，
 */
public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        //左边的delegate
        loadRootFragment(R.id.vertical_list_container,listDelegate);
        //设置右侧第一个分类显示，默认显示分类一
        replaceLoadRootFragment(R.id.sort_list_container,ContentDelegate.newInstance(1),false);
    }
}
