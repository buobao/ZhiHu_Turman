package com.zhihu.turman.app.entity.weather;

import com.zhihu.turman.app.entity.BaseEntity;

/**
 * Created by dqf on 2016/4/5.
 */
public class Pm25 extends BaseEntity {
    public String key;
    public int show_desc;
    public String dateTime;
    public String cityName;
    public Pm25Info pm25;
}
