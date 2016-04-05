package com.zhihu.turman.app.net.service;

import com.zhihu.turman.app.entity.WeatherResult;
import com.zhihu.turman.app.net.NetworkClient;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/4.
 */
public interface WeatherService {
    @GET(NetworkClient.WEATHER)
    Observable<WeatherResult> getWeather(@Query("cityname")String cityname,@Query("key")String key);
}
