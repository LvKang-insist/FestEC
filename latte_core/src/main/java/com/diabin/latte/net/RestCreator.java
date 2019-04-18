package com.diabin.latte.net;

import android.util.Log;

import com.diabin.latte.app.ConfigType;
import com.diabin.latte.app.Latte;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Copyright (C)
 *
 * @file: RestCreator
 * @author: 345
 * @Time: 2019/4/17 19:19
 * @description: ${DESCRIPTION}
 */
public class RestCreator {

    private static final class ParamsHolder{
         static final WeakHashMap<String ,Object> PARAMS = new WeakHashMap<>();
    }
    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }


    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                //设置网络请求的 url地址
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                //依赖中引入的转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OkHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient().newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder{

        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
