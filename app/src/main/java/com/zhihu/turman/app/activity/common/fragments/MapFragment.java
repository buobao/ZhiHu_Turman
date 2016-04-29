package com.zhihu.turman.app.activity.common.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.zhihu.turman.app.R;
import com.zhihu.turman.app.activity.common.CommonActivity;
import com.zhihu.turman.app.base.BasecCommonFragment;
import com.zhihu.turman.app.utils.baidu.MyLocationListener;

import java.util.List;

import butterknife.Bind;

/**
 * Created by dqf on 2016/4/28.
 */
public class MapFragment extends BasecCommonFragment implements CommonActivity.MapMenuListener {

    private InputMethodManager mInputMethodManager;

    @Bind(R.id.bmapView)
    protected MapView mMapView;
    @Bind(R.id.local_search)
    protected EditText mEditText;

    private boolean mSearchShow = false;

    private BaiduMap mMap;

    private LatLng cenpt;

    //百度定位
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            mLocationClient.stop();  //结束定位
            //地图定位到当前位置坐标
            cenpt = new LatLng(bundle.getDouble("lat"), bundle.getDouble("lng"));

            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(cenpt)
                    .zoom(15)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mMap.setMapStatus(mMapStatusUpdate);

            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.map_icon_ar_tag);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(cenpt)
                    .icon(bitmap);
            mMap.addOverlay(option);
        }
    };
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener(mHandler);

    PoiSearch mPoiSearch;
    //map settings
    boolean[] selected = new boolean[]{false,false,false,false};

    @Override
    protected void init() {
        mInputMethodManager  = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        SDKInitializer.initialize(mContext);
        mLocationClient = new LocationClient(mContext);     //声明LocationClient类
        initLocation();
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        mLocationClient.start();
        mLocationClient.requestLocation();

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener(){
            public void onGetPoiResult(PoiResult result){
                Toast.makeText(mContext, "onGetPoiResult", Toast.LENGTH_SHORT).show();
                //获取POI检索结果
                if (result != null) {
                    List<PoiInfo> list = result.getAllPoi();
                    if (list != null && list.size() > 0) {
                        for (PoiInfo info : list) {
                            LatLng tmp = info.location;
                            BitmapDescriptor bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.map_pin);
                            OverlayOptions option = new MarkerOptions()
                                    .position(tmp)
                                    .icon(bitmap);
                            mMap.addOverlay(option);
                        }
                    }
                }
            }
            public void onGetPoiDetailResult(PoiDetailResult result){
                //获取Place详情页检索结果
                Toast.makeText(mContext, "onGetPoiDetailResult", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //定位参数设置
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    public int getLayout() {
        return R.layout.frg_map;
    }

    @Override
    public void initView(View view) {
        mMap = mMapView.getMap();

        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && mSearchShow) {
                    //Toast.makeText(mContext,mEditText.getText().toString(),Toast.LENGTH_SHORT).show();

                    if (mEditText.getText() != null && !"".equals(mEditText.getText().toString())) {
                        mPoiSearch.searchNearby((new PoiNearbySearchOption())
                                .location(cenpt)
                                .radius(10000)
                                .keyword(mEditText.getText().toString())
                                .pageCapacity(50));
                    }
                    hideSearch();
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient = null;
        }
        mPoiSearch.destroy();
        super.onDestroy();
    }

    @Override
    public void mapExchange(int itemId) {
        switch (itemId){
            case R.id.local_search:
                if (mSearchShow){
                    hideSearch();
                } else {
                    showSearch();
                }
                break;
            case R.id.road_search:
                break;
            case R.id.normal_map:
                mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.real_map:
                mMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.blank_map:
                mMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                break;
            case R.id.map_setting:
                new AlertDialog.Builder(getContext())
                        .setTitle("地图设置")
                        .setIcon(R.drawable.ic_menu_share)
                        .setCancelable(false)
                        .setMultiChoiceItems(R.array.map_setting,selected, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                selected[which] = isChecked;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //开启交通图
                                if (selected[0]){
                                    mMap.setTrafficEnabled(true);
                                } else {
                                    mMap.setTrafficEnabled(false);
                                }
                                //楼块效果
                                if (selected[1]){
                                    mMap.setBuildingsEnabled(true);
                                } else {
                                    mMap.setBuildingsEnabled(false);
                                }

                                //定位层
                                if (selected[2]){
                                    mMap.setMyLocationEnabled(true);
                                } else {
                                    mMap.setMyLocationEnabled(false);
                                }
                                //城市热力圈
                                if (selected[3]){
                                    mMap.setBaiduHeatMapEnabled(true);
                                } else {
                                    mMap.setBaiduHeatMapEnabled(false);
                                }
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                break;
        }
    }

    private void hideSearch(){
        mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);   //隐藏软键盘
        mEditText.setVisibility(View.GONE);
        mEditText.setText("");
        mSearchShow = false;
    }

    private void showSearch(){
        mEditText.setVisibility(View.VISIBLE);
        mInputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);  //显示键盘
        mSearchShow = true;
    }

}
