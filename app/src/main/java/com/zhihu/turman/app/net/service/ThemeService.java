package com.zhihu.turman.app.net.service;

import com.zhihu.turman.app.entity.ThemeResult;
import com.zhihu.turman.app.net.NetworkClient;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by dqf on 2016/3/30.
 */
public interface ThemeService {

    @GET(NetworkClient.THEMES)
    Observable<ThemeResult> getThemes();
}
