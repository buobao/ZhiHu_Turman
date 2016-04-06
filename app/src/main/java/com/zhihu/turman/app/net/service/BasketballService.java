package com.zhihu.turman.app.net.service;

import com.zhihu.turman.app.entity.BasketballResult;
import com.zhihu.turman.app.net.NetworkClient;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dqf on 2016/4/6.
 */
public interface BasketballService {
    @GET(NetworkClient.NBA)
    Observable<BasketballResult> getGames(@Query("key")String key);
}
