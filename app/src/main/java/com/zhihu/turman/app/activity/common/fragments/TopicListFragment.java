package com.zhihu.turman.app.activity.common.fragments;

import android.os.Bundle;
import android.widget.Toast;

import com.zhihu.turman.app.activity.common.CommonActivity;
import com.zhihu.turman.app.activity.common.CommonEnum;
import com.zhihu.turman.app.base.BaseListFragment;
import com.zhihu.turman.app.entity.Topic;
import com.zhihu.turman.app.entity.TopicResult;
import com.zhihu.turman.app.net.NetworkClient;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dqf on 2016/3/30.
 */
public class TopicListFragment extends BaseListFragment<Topic,TopicListAdapter> {

    @Override
    protected TopicListAdapter getAdapter() {
        return new TopicListAdapter(getContext(),mEntityList);
    }

    @Override
    protected Subscription loadDate() {
        if (mBundle != null){
            int id = mBundle.getInt(THEME_ID,-1);
            return Observable.just(id)
                    .flatMap(new Func1<Integer, Observable<TopicResult>>() {
                        @Override
                        public Observable<TopicResult> call(Integer integer) {
                            return NetworkClient.getTopicService().getTopics(integer);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<TopicResult>() {
                        @Override
                        public void call(TopicResult topicResult) {
                            if (topicResult.stories != null && topicResult.stories.size() > 0){
                                for (Topic t:topicResult.stories){
                                    mEntityList.add(t);
                                }
                                mAdapter.notifyDataSetChanged();
                                setStatus(STATE_NORMAL);
                            } else {
                                if (mEntityList.size() == 0) {
                                    setStatus(STATE_ERROR);
                                    setErrorMessage("没有数据");
                                } else {
                                    setStatus(STATE_NOMOREDATA);
                                }
                            }
                            mRefreshLayout.setRefreshing(false);
                        }
                    });
        }
        return null;
    }

    @Override
    protected void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(CommonActivity.COMMON_TITLE, "新闻");
        bundle.putInt(CommonActivity.FRAGMENT_CLZ, CommonEnum.NEWS.getValue());
        bundle.putInt(CommonActivity.NEWS_ID, mEntityList.get(position).id);
        mApp.openCommon(getActivity(),bundle);
    }
}
