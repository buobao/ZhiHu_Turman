package com.zhihu.turman.app.net;

import com.zhihu.turman.app.net.service.ThemeService;
import com.zhihu.turman.app.net.service.TopicService;
import com.zhihu.turman.app.net.service.WeatherService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dqf on 2016/3/29.
 */
public class NetworkClient {
    private static Retrofit client = null;
    private static Retrofit onebox_client = null;

    public static final String BASE_URL = "http://news-at.zhihu.com/api/";
    public static final String ONEBOX_URL = "http://op.juhe.cn/onebox/";
    public static final String DATA_KEY = "bbf1715173894b79982cdb42d59a8378";

    public static final String THEMES = "4/themes";          //获取主题
    public static final String TOPIC = "4/theme/{id}";       //指定主题下的新闻
    public static final String NEWS = "4/news/{id}";         //获取指定新闻内容

    public static final String WEATHER = "weather/query?";   //查询天气

    private static ThemeService themeService = null;
    private static TopicService topicService = null;
    private static WeatherService weatherService = null;
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

    public static Retrofit getOneboxClient(){
        OkHttpClient okHttpClient = new OkHttpClient();

        if (onebox_client == null) {
            onebox_client = new Retrofit.Builder()
                    .baseUrl(ONEBOX_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return onebox_client;
    }

    public static ThemeService getThemeService(){
        if (themeService == null){
            themeService = getClient().create(ThemeService.class);
        }
        return themeService;
    }

    public static TopicService getTopicService(){
        if (topicService == null) {
            topicService = getClient().create(TopicService.class);
        }
        return topicService;
    }

    public static WeatherService getWeatherService(){
        if (weatherService == null) {
            weatherService =getOneboxClient().create(WeatherService.class);
        }
        return weatherService;
    }


}
