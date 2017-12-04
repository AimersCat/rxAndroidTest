package com.rxadnroid.rxandroidtest.app.rxhttp;

import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 子非鱼 on 2017/2/9.
 */
public class RetrofitService {
    //    private static final String API = "https://api.github.com";

//    private static final String API = "http://ht.yuhong.com.cn:6089/api_hongtu/";

    private static final String API = "http://oa.ytdinfo.cn/ytdoa-api-dev/";

    private static String API_URL = "http://apistore.baidu.com";

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10;


    protected RetrofitService() {
        /**
         * 对所有请求添加请求头
         */
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder builder1 = request.newBuilder();
                        Request build = builder1
                                .addHeader("app_key", "1")
                                .addHeader("sign", "1")
                                .addHeader("timestamp", "1")
                                .addHeader("ostype", "Android")
                                .addHeader("system_model", Build.MODEL)
                                .addHeader("osversion", Build.VERSION.RELEASE)
                                .addHeader("devicetoken", "")
                                .addHeader("appversion", "")
                                .addHeader("Accept", "*/*")
                                .build();
                        return chain.proceed(build);
                    }
                })
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();




        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private volatile static RetrofitService instance = null;

    private Retrofit retrofit;

    public static RetrofitService getInstance() {
        if (instance == null) {
            synchronized (RetrofitService.class) {
                if (instance == null) {
                    instance = new RetrofitService();
                }
            }
        }
        return instance;
    }

    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }

}

