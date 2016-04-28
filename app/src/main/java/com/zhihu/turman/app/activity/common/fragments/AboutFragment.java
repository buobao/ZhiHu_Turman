package com.zhihu.turman.app.activity.common.fragments;

import android.view.View;
import android.widget.TextView;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.base.BasecCommonFragment;

import butterknife.Bind;

/**
 * Created by dqf on 2016/4/8.
 */
public class AboutFragment extends BasecCommonFragment {
    @Bind(R.id.about_text)
    protected TextView mAbout;

    @Override
    public int getLayout() {
        return R.layout.frg_about;
    }

    @Override
    public void initView(View view) {
        mAbout.setText("本应用包括:\n<知乎日报热点>\n<天气查询>\n<NBA赛事查询>\n<NBA热点新闻>\n<足球赛事查询>\n<足球热点新闻>\n<百度地图>\nAPI收集中，敬请期待!<(￣︶￣)>");
    }
}
