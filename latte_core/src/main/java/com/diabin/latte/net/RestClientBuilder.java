package com.diabin.latte.net;

import android.content.Context;

import com.diabin.latte.net.callback.IError;
import com.diabin.latte.net.callback.IFailure;
import com.diabin.latte.net.callback.IReqeust;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.ui.LoaderStyle;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Copyright (C)
 *
 * @file: RestClientBuilder
 * @author: 345
 * @Time: 2019/4/17 19:48
 * @description: ${DESCRIPTION}
 */
public class RestClientBuilder {
    private String mUrl = null;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private IReqeust mIrequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=urf-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IReqeust iReqeust) {
        this.mIrequest = iReqeust;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder ifailure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder ierror(IError iError) {
        this.mIError = iError;
        return this;
    }
    public final RestClientBuilder loader(Context context,LoaderStyle style){
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle =  LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mIrequest, mISuccess, mIFailure, mIError, mBody,mLoaderStyle,mContext);
    }

}
