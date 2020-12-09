package com.jvtc.catcampus_teacher.http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpUtils {
    public static String baseUrl = "https://jiu.notbucai.com/shi/api/";
    public static String notbucaiUrl = "https://jvtc.notbucai.com/";
    public static String ncgameUrl = "https://api.ncgame.cc/";

    /**
     * 自定义字符串解析器
     */
    private static Converter.Factory Rxfactory = new Converter.Factory() {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return new Converter<ResponseBody, JSONObject>() {
                @Override
                public JSONObject convert(ResponseBody value) throws IOException {
                    JSONObject jsonObject = null;
                    try {
                         jsonObject = new JSONObject(value.string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return jsonObject;
                }
            };
        }
    };

    private static Converter.Factory factory = new Converter.Factory() {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }
    };

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build();

    public static synchronized Retrofit createRxRetrofit(String baseUrl){
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(Rxfactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static void createHttp(Observable<JSONObject> call,HttpCallBack httpCallBack){
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {
                        httpCallBack.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallBack.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        httpCallBack.onNext(jsonObject);
                    }
                });
    }



}
