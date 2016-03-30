package com.zhihu.turman.app.net;

import com.zhihu.turman.app.net.service.ThemeService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dqf on 2016/3/29.
 */
public class NetworkClient {
    private static Retrofit client = null;

    public static final String BASE_URL = "http://news-at.zhihu.com/api/";

    public static final String THEMES = "4/themes";

    private static ThemeService themeService = null;
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

    public static ThemeService getThemeService(){
        if (themeService == null){
            themeService = getClient().create(ThemeService.class);
        }
        return themeService;
    }


}
