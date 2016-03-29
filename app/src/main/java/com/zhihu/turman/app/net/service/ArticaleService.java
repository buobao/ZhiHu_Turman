package com.zhihu.turman.app.net.service;

import com.zhihu.turman.app.entity.ArticaleResult;
import com.zhihu.turman.app.net.NetworkClient;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by dqf on 2016/3/29.
 */
public interface ArticaleService {
    @GET(NetworkClient.GET_POSTS)
    Observable<ArticaleResult> getPosts(@Path("timestamp")long timestamp);
}
