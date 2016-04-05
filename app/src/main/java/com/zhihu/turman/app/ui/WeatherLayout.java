package com.zhihu.turman.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhihu.turman.app.R;

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
        setOrientation(VERTICAL);
        addView(view);
    }

    public void setDay(String str){
        mDay.setText(str);
    }

    public void setDate(String str){
        mItemDate.setText(str);
    }

    public void setDay1(String str){
        mDay1.setText(str);
    }

    public void setDay2(String str){
        mDay2.setText(str);
    }

    public void setDay3(String str){
        mDay3.setText(str);
    }

    public void setDay4(String str){
        mDay4.setText(str);
    }

    public void setDay5(String str){
        mDay5.setText(str);
    }

    public void setNight1(String str){
        mNight1.setText(str);
    }
    public void setNight2(String str){
        mNight2.setText(str);
    }
    public void setNight3(String str){
        mNight3.setText(str);
    }
    public void setNight4(String str){
        mNight4.setText(str);
    }
    public void setNight5(String str){
        mNight5.setText(str);
    }
}
