package com.zhihu.turman.app.entity.weather;

import com.zhihu.turman.app.entity.BaseEntity;

/**
 * Created by dqf on 2016/4/5.
 */
public class Realtime extends BaseEntity {
    public String city_code;
    public String city_name;
    public String date;
    public String time;
    public int week;
    public String moon;
    public long dataUptime;
    public CurrWeather weather;
    public Wind wind;
}
