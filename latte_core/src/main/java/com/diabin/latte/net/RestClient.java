package com.diabin.latte.net;

import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.diabin.latte.net.callback.IError;
import com.diabin.latte.net.callback.IFailure;
import com.diabin.latte.net.callback.IReqeust;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.net.callback.RequestCallback;
import com.diabin.latte.ui.LatteLoader;
import com.diabin.latte.ui.LoaderStyle;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

/**
 * Copyright (C)
 *
 * @file: RestClient
 * @author: 345
 * @Time: 2019/4/17 13:14
 * @description: ${DESCRIPTION}
 */
public class RestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IReqeust REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    public RestClient(String url,
                      Map<String, Object> params,
                      IReqeust reqeust,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      LoaderStyle LoaderStyle,
                      Context context) {
        this.URL = url;
        params.putAll(params);
        this.REQUEST = reqeust;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.LOADER_STYLE = LoaderStyle;
        this.CONTEXT = context;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        if (LOADER_STYLE != null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            default:
                break;
        }
        if (call != null) {
            /**
             *  发送请求
             *      同步：调用Call对象的execute()，返回结果的响应体
             *      异步：调用Call对象的enqueue()，参数是一个回调
             */
            System.out.println("++++++++++++++++++++++++++++");
            ;
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallback(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

}
