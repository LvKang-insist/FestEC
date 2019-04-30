package com.admin.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.diabin.latte.deleggate.LatteDelegate;
import com.diabin.latte.ec.sign.SignHandler;
import com.diabin.latte.net.RestClient;
import com.diabin.latte.net.callback.IError;
import com.diabin.latte.net.callback.IFailure;
import com.diabin.latte.net.callback.ISuccess;

/**
 * Copyright (C)
 * 文件名称: ExampleDelegate
 * 创建人: 345
 * 创建时间: 2019/4/16 18:45
 * 描述: ${DESCRIPTION}
 */
public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout()  {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    private void testRestClient(){
               RestClient.builder()
                       .url("7myrTU775c5f6e69260fc1ab51784a399333d5030c36288?uri=sign_in")
                       .loader(getContext())
                       .success(new ISuccess() {
                           @Override
                           public void OnSuccess(String response) {

                               Log.e("TAG", "OnSuccess: "+response );
//                               Toast.makeText(Latte.getApplication(), response, Toast.LENGTH_SHORT).show();
                           }
                       })
                       .ifailure(new IFailure() {
                           @Override
                           public void onFailure() {

                           }
                       })
                       .ierror(new IError() {
                           @Override
                           public void OnError(int code, String msg) {

                           }
                       })
                       .build()
                       .post();
           }
}
