package com.zhihu.turman.app.activity.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.base.BaseContextFragment;
import com.zhihu.turman.app.base.BaseListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dqf on 2016/3/30.
 */
public class CommonActivity extends AppCompatActivity {

    public static final String TAG = "CommonActivity";

    public static final String COMMON_TITLE= "title";
    public static final String FRAGMENT_CLZ = "clz";
    public static final String THEME_ID = "id";
    public static final String NEWS_ID = "nId";
    public static final String NEWS_PIC = "pic";
    public static final String MAP_MENU = "map_menu";

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;

    private Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_common);
        ButterKnife.bind(this);

        mBundle = getIntent().getExtras();
        mToolbar.setNavigationIcon(R.drawable.back32);
        if (mBundle != null) {
            mToolbar.setTitle(mBundle.getString(COMMON_TITLE));
            try {
                mFragment = (Fragment) CommonEnum.getPageByValue(mBundle.getInt(FRAGMENT_CLZ)).getClz().newInstance();
                mFragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                if (mBundle.getInt(THEME_ID, -1) > 0){
                    bundle.putInt(BaseListFragment.THEME_ID, mBundle.getInt(THEME_ID));
                }
                if (mBundle.getInt(NEWS_ID, -1) > 0){
                    bundle.putInt(BaseContextFragment.ENTITY_ID, mBundle.getInt(NEWS_ID));
                    if (!"".equals(mBundle.getString(NEWS_PIC, ""))){
                        bundle.putString(BaseContextFragment.ENTITY_PIC, mBundle.getString(NEWS_PIC));
                    }
                }
                mFragment.setArguments(bundle);
                transaction.replace(R.id.common_fragment, mFragment);
                transaction.commit();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (mBundle != null && mBundle.getBoolean(MAP_MENU,false)) {
            initMenu();
        }


    }

    private void initMenu() {
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MapMenuListener listener = (MapMenuListener) mFragment;
                listener.mapExchange(item.getItemId());
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mBundle != null && mBundle.getBoolean(MAP_MENU,false)){
            getMenuInflater().inflate(R.menu.act_common_map,menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    public interface MapMenuListener{
        void mapExchange(int itemId);
    }
}
