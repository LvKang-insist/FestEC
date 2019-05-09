package com.diabin.latte.ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ec.main.personal.PersonalDelegate;
import com.diabin.latte.net.RestClient;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.util.dimen.SetToolBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Copyright (C)
 *
 * @file: OrderListDelegate
 * @author: 345
 * @Time: 2019/5/8 14:04
 * @description: 订单管理
 */
public class OrderListDelegate extends LatteDelegate {

    private String mType = null;

    @BindView(R2.id.tb_shop_cart)
    Toolbar mToolbar = null;
    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        assert args != null;
        mType = args.getString(PersonalDelegate.ORDER_TYPE);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .loader(getContext())
                .url("order_list.json")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final ArrayList<MultipleItemEntity> convert =
                                new OrderListDataConverter().setJsonData(response).convert();
                        OrderListAdapter adapter = new OrderListAdapter(convert);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}
