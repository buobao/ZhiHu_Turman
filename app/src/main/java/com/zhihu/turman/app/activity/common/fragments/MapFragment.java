package com.zhihu.turman.app.activity.common.fragments;

import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.zhihu.turman.app.R;
import com.zhihu.turman.app.base.BasecCommonFragment;

import butterknife.Bind;

/**
 * Created by dqf on 2016/4/28.
 */
public class MapFragment extends BasecCommonFragment {

    @Bind(R.id.bmapView)
    protected MapView mMapView;

    @Override
    protected void init() {
        SDKInitializer.initialize(mContext);
    }

    @Override
    public int getLayout() {
        return R.layout.frg_map;
    }

    @Override
    public void initView(View view) {

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
        super.onDestroy();
        mMapView.onDestroy();

    }
}
