package com.zhihu.turman.app.activity.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.TurmanApplication;
import com.zhihu.turman.app.activity.common.CommonActivity;
import com.zhihu.turman.app.activity.common.CommonEnum;
import com.zhihu.turman.app.entity.Theme;
import com.zhihu.turman.app.entity.ThemeResult;
import com.zhihu.turman.app.net.NetworkClient;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.home_list)
    protected ListView mListView;
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawer;
    @Bind(R.id.nav_view)
    protected NavigationView mNavigationView;
    @Bind(R.id.home_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    private ThemeListAdapter themeListAdapter;
    //主题数据列表
    private LinkedList<Theme> mEntityList = new LinkedList<>();

    private TurmanApplication mApp;

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        ButterKnife.bind(this);


        mApp = (TurmanApplication)getApplication();
        mToolbar.setTitle("知乎热点");
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mEntityList.clear();
                loadDate();
            }
        });

        themeListAdapter = new ThemeListAdapter(mEntityList, this);
        mListView.setAdapter(themeListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(CommonActivity.COMMON_TITLE, mEntityList.get(position).name);
                bundle.putInt(CommonActivity.FRAGMENT_CLZ, CommonEnum.TOPIC.getValue());   //传枚举值，再转查找fragment class
                bundle.putInt(CommonActivity.THEME_ID, mEntityList.get(position).id);
                mApp.openCommon(HomeActivity.this,bundle);
            }
        });
        //加载数据
        loadDate();
    }

    private void loadDate(){
        mSubscription = Observable.just(System.currentTimeMillis())
                .flatMap(new Func1<Long, Observable<ThemeResult>>() {
                    @Override
                    public Observable<ThemeResult> call(Long aLong) {
                        return NetworkClient.getThemeService().getThemes();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ThemeResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ThemeResult articaleResult) {
                        for (Theme a : articaleResult.others) {
                            mEntityList.add(a);
                        }
                        themeListAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_weather) {
            Bundle bundle = new Bundle();
            bundle.putString(CommonActivity.COMMON_TITLE, "天气查询");
            bundle.putInt(CommonActivity.FRAGMENT_CLZ, CommonEnum.WEATHER.getValue());   //传枚举值，再转查找fragment class
            mApp.openCommon(HomeActivity.this,bundle);
        } else if (id == R.id.nav_nba) {
            Bundle bundle = new Bundle();
            bundle.putString(CommonActivity.COMMON_TITLE, "NBA赛事");
            bundle.putInt(CommonActivity.FRAGMENT_CLZ, CommonEnum.BASKETBALL.getValue());   //传枚举值，再转查找fragment class
            mApp.openCommon(HomeActivity.this,bundle);
        } else if (id == R.id.about) {
            Bundle bundle = new Bundle();
            bundle.putString(CommonActivity.COMMON_TITLE, "关于");
            bundle.putInt(CommonActivity.FRAGMENT_CLZ, CommonEnum.ABOUT.getValue());
            mApp.openCommon(HomeActivity.this,bundle);
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
