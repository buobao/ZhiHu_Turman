package com.zhihu.turman.app.activity.common;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.activity.common.fragments.NewsFragment;
import com.zhihu.turman.app.activity.common.fragments.TopicListFragment;
import com.zhihu.turman.app.activity.common.fragments.WeatherFragment;

/**
 * Created by dqf on 2016/3/31.
 */
public enum CommonEnum {
    TOPIC(0, R.string.topic_1,TopicListFragment.class),
    NEWS(1,R.string.topic_1, NewsFragment.class),
    WEATHER(2,R.string.topic_1, WeatherFragment.class);

    private int title;
    private Class<?> clz;
    private int value;

    private CommonEnum(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static CommonEnum getPageByValue(int val) {
        for (CommonEnum p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }
}
