package com.sososeen09.sample.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 2018/5/10.
 *
 * @author sososeen09
 */

public class RetrofitManager {
    private static String realBaseUrl = "https://easy-mock.com/mock/5a3a04f70bd9de68557efb6a/";
//                                       https://easy-mock.com/mock/5a3a04f70bd9de68557efb6a/user/attrs
    private static HttpService sHttpService = null;
    private static OkHttpClient sOkHttpClient = null;

    public static HttpService getHttpService() {
        if (sHttpService == null) {
            sHttpService = new Retrofit.Builder()
                    .baseUrl(realBaseUrl)
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(AttributeListConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(HttpService.class);
        }

        return sHttpService;
    }


    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(60L, TimeUnit.SECONDS)
                    .readTimeout(60L, TimeUnit.SECONDS)
                    .writeTimeout(60L, TimeUnit.SECONDS)
                    .build();
        }

        return sOkHttpClient;
    }


    private static Converter.Factory AttributeListConverterFactory() {
        return null;
    }
}
