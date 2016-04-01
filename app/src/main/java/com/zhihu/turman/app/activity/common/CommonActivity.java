package com.zhihu.turman.app.activity.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zhihu.turman.app.R;
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

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;
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
                Fragment fragment = (Fragment) CommonEnum.getPageByValue(mBundle.getInt(FRAGMENT_CLZ)).getClz().newInstance();
                mFragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                if (mBundle.getInt(THEME_ID, -1) > 0){
                    Bundle bundle = new Bundle();
                    bundle.putInt(BaseListFragment.THEME_ID, mBundle.getInt(THEME_ID));
                    fragment.setArguments(bundle);
                }
                transaction.replace(R.id.common_fragment, fragment);
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


    }
}
