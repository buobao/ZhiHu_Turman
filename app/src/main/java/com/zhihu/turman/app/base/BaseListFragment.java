package com.zhihu.turman.app.base;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by dqf on 2016/3/30.
 */
public abstract class BaseListFragment<T extends BaseEntity,A extends BaseListAdapter<T>> extends Fragment {

    public static final int STATE_LOADING = 0;
    public static final int STATE_NORMAL = 1;
    public static final int STATE_RELOADING = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_NOMOREDATA = 4;

    public static final String THEME_ID = "id";

    @Bind(R.id.loading_layout)
    protected LinearLayout mLoadingLayout;
    @Bind(R.id.load_error)
    protected TextView mErrorTextView;
    @Bind(R.id.refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.data_list)
    protected RecyclerView mList;

    protected MyLayoutManager mLayoutManager;

    protected Bundle mBundle;

    protected LinkedList<T> mEntityList = new LinkedList<>();
    protected A mAdapter;

    protected int mCurrStatus = STATE_LOADING;

    protected List<Subscription> mSubscriptions = new ArrayList<>();

    protected A getAdapter(){
        return null;
    }

    protected abstract Subscription loadDate();

    protected void load(){
        mSubscriptions.add(loadDate());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_base_list,container,false);
        ButterKnife.bind(this,view);

        mBundle = getArguments();
        mLayoutManager = new MyLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setAutoMeasureEnabled(false);
        mList.setLayoutManager(mLayoutManager);
        mList.setHasFixedSize(false);
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));

        mAdapter = getAdapter();
        if (mAdapter != null) {
            mList.setAdapter(mAdapter);
        }

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mEntityList.clear();
                load();
            }
        });

        load();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscriptions != null && mSubscriptions.size() > 0) {
            for (Subscription s:mSubscriptions){
                if (s!= null && !s.isUnsubscribed()){
                    s.unsubscribe();
                }
            }
        }
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

    public void setErrorMessage(String str) {
        mErrorTextView.setText(str);
    }


    //这里遇到一个问题：RecyclerView中每个item都会占一屏的高度，所以重写layoutmanager来解决这个问题
    public class SyLinearLayoutManager extends LinearLayoutManager {

        private static final int CHILD_WIDTH = 0;
        private static final int CHILD_HEIGHT = 1;
        private static final int DEFAULT_CHILD_SIZE = 100;

        private final int[] childDimensions = new int[2];

        private int childSize = DEFAULT_CHILD_SIZE;
        private boolean hasChildSize;

        @SuppressWarnings("UnusedDeclaration")
        public SyLinearLayoutManager(Context context) {
            super(context);
        }

        @SuppressWarnings("UnusedDeclaration")
        public SyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        private int[] mMeasuredDimension = new int[2];


        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            final int widthMode = View.MeasureSpec.getMode(widthSpec);
            final int heightMode = View.MeasureSpec.getMode(heightSpec);
            final int widthSize = View.MeasureSpec.getSize(widthSpec);
            final int heightSize = View.MeasureSpec.getSize(heightSpec);
            int width = 0;
            int height = 0;
            for (int i = 0; i < getItemCount(); i++) {
                try {
                    measureScrapChild(recycler, i,
                            widthSpec,
                            View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                            mMeasuredDimension);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                if (getOrientation() == HORIZONTAL) {
                    width = width + mMeasuredDimension[0];
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    height = height + mMeasuredDimension[1];
                    if (i == 0) {
                        width = mMeasuredDimension[0];
                    }
                }
            }

            switch (widthMode) {
                case View.MeasureSpec.EXACTLY:
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }
            setMeasuredDimension(widthSpec, height);

        }

        private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension) {
            View view = recycler.getViewForPosition(position);

            if (view != null) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        getPaddingTop() + getPaddingBottom(), p.height);
                view.measure(widthSpec, childHeightSpec);
                measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                recycler.recycleView(view);
            }
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);
        }
    }

    public class MyLayoutManager extends LinearLayoutManager {

        public MyLayoutManager(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }


        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec,int heightSpec) {
            View view = recycler.getViewForPosition(0);
            if(view != null){
                measureChild(view, widthSpec, heightSpec);
                int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                int measuredHeight = view.getMeasuredHeight();
                setMeasuredDimension(measuredWidth, measuredHeight);
            }
        }
    }

}






































