package com.diabin.latte.net.callback;

import android.os.Handler;

import com.diabin.latte.ui.LatteLoader;
import com.diabin.latte.ui.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright (C)
 *
 * @file: RequestCallback
 * @author: 345
 * @Time: 2019/4/17 20:56
 * @description: ${DESCRIPTION}
 */
public class RequestCallback implements Callback<String> {
    private final IReqeust REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private final Handler HEADLER = new Handler();

    public RequestCallback(IReqeust request, ISuccess success, IFailure failure, IError error, LoaderStyle loaderStyle) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = loaderStyle;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.OnSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.OnError(response.code(), response.message());
            }
        }

        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onReqeustEnd();
        }
        stopLoading();
    }

    private void stopLoading() {
        if (LOADER_STYLE != null) {
            HEADLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            }, 10000);
        }
    }
}
