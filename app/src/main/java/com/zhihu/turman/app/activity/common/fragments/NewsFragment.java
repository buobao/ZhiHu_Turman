package com.zhihu.turman.app.activity.common.fragments;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhihu.turman.app.R;
import com.zhihu.turman.app.base.BaseContextFragment;
import com.zhihu.turman.app.entity.News;
import com.zhihu.turman.app.net.NetworkClient;

import butterknife.Bind;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/3.
 */
public class NewsFragment extends BaseContextFragment<News> {
    @Bind(R.id.context_pic)
    protected ImageView mPic;
    @Bind(R.id.context_title)
    protected TextView mTitle;
    @Bind(R.id.context_web)
    protected WebView mWeb;

    @Override
    protected int getLayout() {
        return R.layout.frg_base_context;
    }

    @Override
    protected Observable<News> getObservable(Integer integer) {
        return NetworkClient.getTopicService().getNews(integer);
    }

    @Override
    protected void fillUI() {
        if (mEntity != null) {
            String pic_url = mEntity.theme.thumbnail;
            if (!"".equals(pic_url)) {
                Glide.with(getActivity())
                        .load(pic_url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.img_rect_placeholder)
                        .error(R.drawable.default_photo)
                        .centerCrop()
                        .into(mPic);
            }

            mTitle.setText(mEntity.title);

            String html = mEntity.body;
            html = "<html><title></title><body>"+html+"</body></html>";
            mWeb.loadDataWithBaseURL(null,html,"text/html","utf-8", null);
            mWeb.getSettings().setJavaScriptEnabled(true);
        }

    }
}




















