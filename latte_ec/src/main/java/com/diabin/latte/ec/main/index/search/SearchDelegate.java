package com.diabin.latte.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.diabin.latte.app.Latte;
import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.net.RestClient;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.util.dimen.SetToolBar;
import com.diabin.latte.util.storage.LattePreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C)
 *
 * @file: SearchDelegate
 * @author: 345
 * @Time: 2019/5/13 15:26
 * @description: ${DESCRIPTION}
 */
public class SearchDelegate extends LatteDelegate {

    @BindView(R2.id.rv_search)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchEdit = null;
    @BindView(R2.id.tb_main_page)
    Toolbar mToolbar = null;
    @BindView(R2.id.clear_history)
    AppCompatTextView mClear = null;


    @OnClick(R2.id.tv_top_search)
    void onClickSearch(){
        RestClient.builder()
                .url("search.json")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String searchItemText= mSearchEdit.getText().toString();
                        //展示一些东西
                    }
                })
                .build()
                .get();
        final String searchItemText= mSearchEdit.getText().toString();
        saveItem(searchItemText);
    }

    @OnClick(R2.id.icon_top_search_back)
    void onClickBack(){
         getSupportDelegate().pop();
    }

    @SuppressWarnings("unchecked")
    private void saveItem(String item){
        if (!StringUtils.isEmpty(item) && !StringUtils.isSpace(item)){
            List<String> history;
            //获取历史记录
            final String historyStr = LattePreference.getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY);
            if (StringUtils.isEmpty(historyStr)){
                history = new ArrayList<>();
            }else {
                //如果历史记录不为空，则解析 存入集合中
                history = JSON.parseObject(historyStr,ArrayList.class);
            }
            //添加新的记录
            history.add(item);
            //将全部记录转换为 json 串保存
            final String json = JSON.toJSONString(history);
            LattePreference.setCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY,json);
        }
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        final List<MultipleItemEntity> data = new SearchDataConverter().convert();
        final SearchAdapter adapter = new SearchAdapter(data);
        mRecyclerView.setAdapter(adapter);

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LattePreference.setCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY,null);
                adapter.getData().clear();
                adapter.notifyDataSetChanged();
            }
        });

        final DividerItemDecoration itemDecoration = new DividerItemDecoration();
        itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }
            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(20,20)
                        .color(Color.GRAY)
                        .build();
            }
        });
    }
}
