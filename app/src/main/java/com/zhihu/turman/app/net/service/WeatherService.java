package com.zhihu.turman.app.net.service;

import com.zhihu.turman.app.entity.News;
import com.zhihu.turman.app.net.NetworkClient;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/4.
 */
public interface WeatherService {
    @GET(NetworkClient.WEATHER)
    Observable<News> getWeather(@Path("cityname")String cityname,@Path("key")String key);
}
