package com.zhihu.turman.app.activity.common.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.Map.LocationName;
import com.zhihu.turman.app.entity.MapResult;
import com.zhihu.turman.app.entity.WeatherResult;
import com.zhihu.turman.app.entity.weather.FutureWeather;
import com.zhihu.turman.app.net.NetworkClient;
import com.zhihu.turman.app.ui.SearchBar;
import com.zhihu.turman.app.ui.WeatherLayout;
import com.zhihu.turman.app.utils.LocationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/4.
 */
public class WeatherFragment extends Fragment {

    @Bind(R.id.frg_weather_search)
    protected SearchBar mSearchBar;
    @Bind(R.id.city_name)
    protected TextView mCityName;
    @Bind(R.id.datetime)
    protected TextView mDateTime;
    @Bind(R.id.current_temperature)
    protected TextView mCurrentTemperature;
    @Bind(R.id.current_humidity)
    protected TextView mCurrentHumidity;
    @Bind(R.id.current_info)
    protected TextView mCurrentInfo;
    @Bind(R.id.current_wind_direct)
    protected TextView mCurrentWindDirect;
    @Bind(R.id.current_wind_power)
    protected TextView mCurrentWindPower;
    @Bind(R.id.chuanyi)
    protected TextView mChuanyi;
    @Bind(R.id.chuanyi_info)
    protected TextView mChuanyiInfo;
    @Bind(R.id.ganmao)
    protected TextView mGanmao;
    @Bind(R.id.ganmao_info)
    protected TextView mGanmaoInfo;
    @Bind(R.id.kongtiao)
    protected TextView mKongtiao;
    @Bind(R.id.kongtiao_info)
    protected TextView mKongtiaoInfo;
    @Bind(R.id.wuran)
    protected TextView mWuran;
    @Bind(R.id.wuran_info)
    protected TextView mWuranInfo;
    @Bind(R.id.xiche)
    protected TextView mXiche;
    @Bind(R.id.xiche_info)
    protected TextView mXicheInfo;
    @Bind(R.id.yundong)
    protected TextView mYundong;
    @Bind(R.id.yundong_info)
    protected TextView mYundongInfo;
    @Bind(R.id.ziwaixian)
    protected TextView mZiwaixian;
    @Bind(R.id.ziwaixian_info)
    protected TextView mZiwaixianInfo;
    @Bind(R.id.curPm)
    protected TextView mCurPm;
    @Bind(R.id.pm25)
    protected TextView mPm25;
    @Bind(R.id.pm10)
    protected TextView mPm10;
    @Bind(R.id.quality)
    protected TextView mQuality;
    @Bind(R.id.pm25_des)
    protected TextView mPm25Des;
    @Bind(R.id.frg_weather_layout)
    protected LinearLayout mLayout;

    private WeatherResult mEntity = new WeatherResult();
    private List<FutureWeather> mListFutureWeather = new ArrayList<>();
    private String mSearchString = null;
    private List<Subscription> mSubscriptions = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_weather_context, container, false);
        ButterKnife.bind(this, view);

        mSearchBar.setSearchOption(new SearchBar.SearchOption() {
            @Override
            public void search() {
                String tmp_str = mSearchBar.getSearchString();
                if (!"".equals(tmp_str)) {
                    mSearchString = tmp_str;
                    loadEntity();
                }
            }
        });

        if (mSearchString == null) {
            Location location = LocationUtils.getLocation(getActivity());
            if (location != null) {
                Map<String,Object> sub_params = new HashMap<>();
                sub_params.put("latlng", location.getLatitude() + "," + location.getLongitude());
                sub_params.put("sensor",true);
                sub_params.put("language","zh-CN");
                mSubscriptions.add(
                        Observable.just(sub_params)
                                .flatMap(new Func1<Map<String, Object>, Observable<MapResult>>() {
                                    @Override
                                    public Observable<MapResult> call(Map<String, Object> stringObjectMap) {
                                        return NetworkClient.getMapService().getAddress((String)stringObjectMap.get("latlng"),(boolean)stringObjectMap.get("sensor"),(String)stringObjectMap.get("language"));
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<MapResult>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onNext(MapResult mapResult) {
                                        if ("OK".equals(mapResult.status)){
                                            for (LocationName n : mapResult.results.get(0).address_components) {
                                                if ("locality".equals(n.types.get(0))) {
                                                    mSearchString = n.short_name;
                                                    break;
                                                }
                                            }
                                            loadEntity();
                                        }
                                    }
                                })
                );
            }
        } else {
            loadEntity();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscriptions.size() > 0) {
            for (Subscription s:mSubscriptions){
                if (s != null && !s.isUnsubscribed()){
                    s.unsubscribe();
                }
            }
        }
    }

    private void loadEntity() {
        Map<String, String> params = new HashMap<>();
        params.put("cityname", mSearchString);
        params.put("key", NetworkClient.WEATHER_KEY);
        mSubscriptions.add(
                Observable.just(params)
                        .flatMap(new Func1<Map<String, String>, Observable<WeatherResult>>() {
                            @Override
                            public Observable<WeatherResult> call(Map<String, String> stringStringMap) {
                                return NetworkClient.getWeatherService().getWeather(stringStringMap.get("cityname"), stringStringMap.get("key"));
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<WeatherResult>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(WeatherResult weatherResult) {
                                if (weatherResult.error_code == 0) {
                                    mEntity = weatherResult;
                                    fillUI();
                                } else {
                                    Toast.makeText(getActivity(), "未能查询到结果", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
        );
    }

    private void fillUI() {
        mCityName.setText(mEntity.result.data.realtime.city_name);
        mDateTime.setText(mEntity.result.data.realtime.date + " " + mEntity.result.data.realtime.time);
        mCurrentTemperature.setText(mEntity.result.data.realtime.weather.temperature + "℃");
        mCurrentHumidity.setText(mEntity.result.data.realtime.weather.humidity + "%");
        mCurrentInfo.setText(mEntity.result.data.realtime.weather.info);
        mCurrentWindDirect.setText(mEntity.result.data.realtime.wind.direct);
        mCurrentWindPower.setText(mEntity.result.data.realtime.wind.power);
        mChuanyi.setText(mEntity.result.data.life.info.chuanyi.get(0));
        mChuanyiInfo.setText(mEntity.result.data.life.info.chuanyi.get(1));
        mGanmao.setText(mEntity.result.data.life.info.ganmao.get(0));
        mGanmaoInfo.setText(mEntity.result.data.life.info.ganmao.get(1));
        mKongtiao.setText(mEntity.result.data.life.info.kongtiao.get(0));
        mKongtiaoInfo.setText(mEntity.result.data.life.info.kongtiao.get(1));
        mWuran.setText(mEntity.result.data.life.info.wuran.get(0));
        mWuranInfo.setText(mEntity.result.data.life.info.wuran.get(1));
        mXiche.setText(mEntity.result.data.life.info.xiche.get(0));
        mXicheInfo.setText(mEntity.result.data.life.info.xiche.get(1));
        mYundong.setText(mEntity.result.data.life.info.yundong.get(0));
        mYundongInfo.setText(mEntity.result.data.life.info.yundong.get(1));
        mZiwaixian.setText(mEntity.result.data.life.info.ziwaixian.get(0));
        mZiwaixianInfo.setText(mEntity.result.data.life.info.ziwaixian.get(1));
        mCurPm.setText(mEntity.result.data.pm25.pm25.curPm);
        mPm25.setText(mEntity.result.data.pm25.pm25.pm25);
        mPm10.setText(mEntity.result.data.pm25.pm25.pm10);
        mQuality.setText(mEntity.result.data.pm25.pm25.quality);
        mPm25Des.setText(mEntity.result.data.pm25.pm25.des);

        mLayout.removeAllViews();
        List<FutureWeather> list = mEntity.result.data.weather;
        for (FutureWeather f:list) {
            WeatherLayout l = new WeatherLayout(getActivity());
            l.setDate(f.date);
            l.setDay("星期" + f.week);
            l.setData(f.info);
            mLayout.addView(l);
        }
    }

}


































