package com.zhihu.turman.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.weather.FutureWeatherInfo;

/**
 * Created by dqf on 2016/4/5.
 */
public class WeatherLayout extends LinearLayout {
    private Context context;
    private TextView mItemDate;
    private TextView mDay;
    private TextView mDay1;
    private TextView mDay2;
    private TextView mDay3;
    private TextView mDay4;
    private TextView mDay5;
    private TextView mNight1;
    private TextView mNight2;
    private TextView mNight3;
    private TextView mNight4;
    private TextView mNight5;
    private TextView mDawn1;
    private TextView mDawn2;
    private TextView mDawn3;
    private TextView mDawn4;
    private TextView mDawn5;
    private LinearLayout mDawnLayout;

    public WeatherLayout(Context context) {
        super(context);
        init(context);
    }

    public WeatherLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        View view = View.inflate(context, R.layout.item_list_weather,null);
        mItemDate = (TextView) view.findViewById(R.id.item_date);
        mDay = (TextView) view.findViewById(R.id.item_day);
        mDay1 = (TextView) view.findViewById(R.id.day_1);
        mDay2 = (TextView) view.findViewById(R.id.day_2);
        mDay3 = (TextView) view.findViewById(R.id.day_3);
        mDay4 = (TextView) view.findViewById(R.id.day_4);
        mDay5 = (TextView) view.findViewById(R.id.day_5);
        mNight1 = (TextView) view.findViewById(R.id.night_1);
        mNight2 = (TextView) view.findViewById(R.id.night_2);
        mNight3 = (TextView) view.findViewById(R.id.night_3);
        mNight4 = (TextView) view.findViewById(R.id.night_4);
        mNight5 = (TextView) view.findViewById(R.id.night_5);
        mDawn1 = (TextView) view.findViewById(R.id.dawn_1);
        mDawn2 = (TextView) view.findViewById(R.id.dawn_2);
        mDawn3 = (TextView) view.findViewById(R.id.dawn_3);
        mDawn4 = (TextView) view.findViewById(R.id.dawn_4);
        mDawn5 = (TextView) view.findViewById(R.id.dawn_5);
        mDawnLayout = (LinearLayout) view.findViewById(R.id.dawn_layout);
        setOrientation(VERTICAL);
        addView(view);
    }

    public void setData(FutureWeatherInfo info){
        mDay1.setText(info.day.get(1));
        mDay2.setText(info.day.get(2));
        mDay3.setText(info.day.get(3));
        mDay4.setText(info.day.get(4));
        mDay5.setText(info.day.get(5));
        mNight1.setText(info.night.get(1));
        mNight2.setText(info.night.get(2));
        mNight3.setText(info.night.get(3));
        mNight4.setText(info.night.get(4));
        mNight5.setText(info.night.get(5));
        if (info.dawn != null) {
            mDawnLayout.setVisibility(VISIBLE);
            mDawn1.setText(info.dawn.get(1));
            mDawn2.setText(info.dawn.get(2));
            mDawn3.setText(info.dawn.get(3));
            mDawn4.setText(info.dawn.get(4));
            mDawn5.setText(info.dawn.get(5));
        }
    }

    public void setDay(String str){
        mDay.setText(str);
    }

    public void setDate(String str){
        mItemDate.setText(str);
    }
}
