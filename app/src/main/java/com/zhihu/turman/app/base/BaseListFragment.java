package com.zhihu.turman.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.BaseEntity;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dqf on 2016/3/30.
 */
public class BaseListFragment<T extends BaseEntity,A extends BaseListAdapter<T>> extends Fragment {

    public static final int STATE_LOADING = 0;
    public static final int STATE_NORMAL = 1;
    public static final int STATE_RELOADING = 2;
    public static final int STATE_ERROR = 3;

    @Bind(R.id.loading_layout)
    protected LinearLayout mLoadingLayout;
    @Bind(R.id.load_error)
    protected TextView mErrorTextView;
    @Bind(R.id.refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.data_list)
    protected RecyclerView mList;

    protected LinearLayoutManager mLayoutManager;

    protected Bundle mBundle;

    protected LinkedList<T> mEntityList;
    protected A mAdapter;

    protected int mCurrStatus = STATE_LOADING;

    protected A getAdapter(){
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_base_list,container,false);
        ButterKnife.bind(getActivity());

        mBundle = getArguments();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(mLayoutManager);
        mList.setHasFixedSize(true);
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mAdapter = getAdapter();
        if (mAdapter != null) {
            mList.setAdapter(mAdapter);
        }

        return view;
    }

    public void setStatus(int status){
        switch (status) {
            case STATE_LOADING:
                mLoadingLayout.setVisibility(View.VISIBLE);
                mErrorTextView.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.GONE);
                break;
            case STATE_NORMAL:
                mLoadingLayout.setVisibility(View.GONE);
                mErrorTextView.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                break;
            case STATE_RELOADING:
                break;
            case STATE_ERROR:
                mLoadingLayout.setVisibility(View.GONE);
                mErrorTextView.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.GONE);
                break;
        }
        mCurrStatus = status;
    }

    public void setSrrorMessage(String str) {
        mErrorTextView.setText(str);
    }

}






































