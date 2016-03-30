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
import android.widget.ListView;
import android.widget.Toast;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.Theme;
import com.zhihu.turman.app.entity.ThemeResult;
import com.zhihu.turman.app.net.NetworkClient;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
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

    private LinkedList<Theme> mEntityList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        ButterKnife.bind(this);
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
        //加载数据
        loadDate();
    }

    private void loadDate(){
        Observable.just(System.currentTimeMillis())
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
                        Toast.makeText(HomeActivity.this, "数据加载失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ThemeResult articaleResult) {
                        for (Theme a : articaleResult.others){
                            mEntityList.add(a);
                        }
                        themeListAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
