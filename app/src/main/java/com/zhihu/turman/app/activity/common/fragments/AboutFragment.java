package com.zhihu.turman.app.activity.common.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhihu.turman.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dqf on 2016/4/8.
 */
public class AboutFragment extends Fragment {
    @Bind(R.id.about_text)
    protected TextView mAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_about,container,false);
        ButterKnife.bind(this, view);

        mAbout.setText("本应用包括:\n<知乎日报热点>\n<天气查询>\n<NBA赛事查询>\n<NBA热点新闻>\n<足球赛事查询>\n<足球热点新闻>\n<博客分享>\nAPI收集中，敬请期待!<(￣︶￣)>");
        return view;
    }
}
