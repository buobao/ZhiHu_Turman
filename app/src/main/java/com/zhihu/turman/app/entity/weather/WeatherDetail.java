package com.zhihu.turman.app.entity.weather;

import com.zhihu.turman.app.entity.BaseEntity;

import java.util.List;

/**
 * Created by dqf on 2016/4/5.
 */
public class WeatherDetail extends BaseEntity {
    public Realtime realtime;
    public Life life;
    public List<FutureWeather> weather;
    public Pm25 pm25;
    public String date;
    public int isForeign;
}
