package com.zhihu.turman.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.BaseEntity;
import com.zhihu.turman.app.net.NetworkClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/3.
 */
public abstract class BaseContextFragment<T extends BaseEntity> extends Fragment {

    public static final String ENTITY_ID = "id";
    public static final String ENTITY_PIC = "pic";

    protected Bundle mBundle;
    protected T mEntity;
    protected List<Subscription> mSubscriptions = new ArrayList<>();
    protected String mPicUrl = null;

    protected int getLayout(){
        return -1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(),container, false);
        ButterKnife.bind(this,view);
        mBundle = getArguments();
        int id = mBundle.getInt(ENTITY_ID, -1);
        if (id > 0) {
            mSubscriptions.add(loadTask(id));
        }
        mPicUrl = mBundle.getString(ENTITY_PIC, "");
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

    private Subscription loadTask(int id){
        return Observable.just(id)
                .flatMap(new Func1<Integer, Observable<T>>() {
                    @Override
                    public Observable<T> call(Integer integer) {
                        return getObservable(integer);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(T t) {
                        mEntity = t;
                        fillUI();
                    }
                });
    }

    protected abstract Observable<T> getObservable(Integer integer);

    protected abstract void fillUI();
}
