package com.zhihu.turman.app.net.service;

import com.zhihu.turman.app.entity.MapResult;
import com.zhihu.turman.app.net.NetworkClient;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dqf on 2016/4/5.
 */
public interface MapService {
    //exp:?latlng=31.1128660714,121.2963159885&sensor=true&language=zh-CN
    @GET(NetworkClient.LOCATION)
    Observable<MapResult> getAddress(@Query("id")String latlng, @Query("sensor")boolean sensor, @Query("language")String language);
}
