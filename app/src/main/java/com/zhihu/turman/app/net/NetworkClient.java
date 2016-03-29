package com.zhihu.turman.app.net;

import com.zhihu.turman.app.net.service.ArticaleService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dqf on 2016/3/29.
 */
public class NetworkClient {
    private static Retrofit client = null;

    public static final String BASE_URL = "http://api.kanzhihu.com/";
    public static final String GET_POSTS = "getposts/{timestamp}";


    private static ArticaleService articaleService = null;
    private NetworkClient(){}

    public static Retrofit getClient(){
        OkHttpClient okHttpClient = new OkHttpClient();

        if (client == null) {
            client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return client;
    }

    public static ArticaleService getArticaleService(){
        if (articaleService == null){
            articaleService = getClient().create(ArticaleService.class);
        }
        return articaleService;
    }


}
