package com.zhihu.turman.app.net.service;

import com.zhihu.turman.app.entity.News;
import com.zhihu.turman.app.entity.TopicResult;
import com.zhihu.turman.app.net.NetworkClient;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by dqf on 2016/3/31.
 */
public interface TopicService {
    @GET(NetworkClient.TOPIC)
    Observable<TopicResult> getTopics(@Path("id")int id);
    @GET(NetworkClient.NEWS)
    Observable<News> getNews(@Path("id")int id);
}
