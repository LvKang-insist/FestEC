package com.diabin.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ec.main.personal.list.ListAdapter;
import com.diabin.latte.ec.main.personal.list.ListBean;
import com.diabin.latte.ec.main.personal.list.ListItemType;
import com.diabin.latte.util.callback.CallBackType;
import com.diabin.latte.util.callback.CallbackManager;
import com.diabin.latte.util.callback.IGlobalCallback;
import com.diabin.latte.util.dimen.SetToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C)
 *
 * @file: SettingsDelegate
 * @author: 345
 * @Time: 2019/5/11 21:13
 * @description:  设置页面
 */
public class SettingsDelegate extends LatteDelegate {

    @BindView(R2.id.settings_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
        final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWTCH)
                .setId(1)
                .setOncheckedchangelistener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        IGlobalCallback callBack = CallbackManager.getInstance()
                                .getCallBack(CallBackType.PUSH);
                        if (callBack!= null){
                            callBack.executeCallBack(isChecked);
                        }
                    }
                })
                .setText("消息推送")
                .build();

        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setDelegate(new AboutDelegate())
                .setId(2)
                .setText("关于")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new SettingsClickListener(this));
    }
}
