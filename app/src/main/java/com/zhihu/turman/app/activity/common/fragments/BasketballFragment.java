package com.zhihu.turman.app.activity.common.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.BasketballResult;
import com.zhihu.turman.app.entity.basketball.GameDay;
import com.zhihu.turman.app.net.NetworkClient;
import com.zhihu.turman.app.ui.BasketballDayItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dqf on 2016/4/6.
 */
public class BasketballFragment extends Fragment {
    @Bind(R.id.day_items)
    protected LinearLayout mLayout;
    @Bind(R.id.data_view)
    protected ScrollView mDataView;
    @Bind(R.id.loading_layout)
    protected LinearLayout mLoadingLayout;
    @Bind(R.id.refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;

    private List<Subscription> mSubscriptions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_basketball_context, container, false);
        ButterKnife.bind(this,view);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();
        return view;
    }

    private void load(){
        mSubscriptions.add(
                Observable.just(NetworkClient.NBA_KEY)
                        .flatMap(new Func1<String, Observable<BasketballResult>>() {
                            @Override
                            public Observable<BasketballResult> call(String s) {
                                return NetworkClient.getBasketballService().getGames(s);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<BasketballResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(BasketballResult basketballResult) {
                                Context context = getActivity();
                                if (basketballResult.error_code == 0){
                                    mLayout.removeAllViews();
                                    List<GameDay> lists = basketballResult.result.list;
                                    for (int i = lists.size()-1;i>=0;i--){
                                        BasketballDayItem item = new BasketballDayItem(context);
                                        item.setData(lists.get(i));
                                        mLayout.addView(item);
                                    }
                                } else {
                                    Toast.makeText(context, "加载数据失败", Toast.LENGTH_SHORT).show();
                                }
                                mDataView.setVisibility(View.VISIBLE);
                                mLoadingLayout.setVisibility(View.GONE);
                            }
                        })
        );
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscriptions.size() > 0) {
            for (Subscription s : mSubscriptions) {
                if (s != null && !s.isUnsubscribed()) {
                    s.unsubscribe();
                }
            }
        }
    }
}
