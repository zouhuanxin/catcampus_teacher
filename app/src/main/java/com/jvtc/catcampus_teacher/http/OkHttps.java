package com.jvtc.catcampus_teacher.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求类
 */
public class OkHttps {

    private static final String TAG = "okhttps";

    /**
     * 网络请求网址
     */
    //private static final String Baseurl = "http://172.20.10.6:8086/";
    public static String Baseurl = "https://jvtc.notbucai.com/";

    public static RxApis getData() {

        /**
         * 实例化 Retrofit对象
         * 构建者方法创建对象 链表式创建方法
         */
        /**
         * 添加 拦截器
         */
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new iii()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Baseurl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        /**
         * 实例化 接口对象
         */
        RxApis dataService = retrofit.create(RxApis.class);

        return dataService;
    }

    //设置插值器（拦截器）
    static class iii implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Headers headers = request.headers();
            int i = 0;
            while (i < headers.size()) {
                String name = headers.name(i);
                String value = headers.value(i);
                Log.i(TAG, "intercept: " + "name=" + name + "  " + value);
                i++;
            }
            okhttp3.Response response = chain.proceed(request);
            return response;
        }


    }
}