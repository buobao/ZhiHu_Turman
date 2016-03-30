package com.zhihu.turman.app.activity.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zhihu.turman.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dqf on 2016/3/30.
 */
public class CommonActivity extends AppCompatActivity {

    public static final String TAG = "CommonActivity";

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_common);
        ButterKnife.bind(this);

        mBundle = getIntent().getExtras();
        mToolbar.setNavigationIcon(R.drawable.back32);
        if (mBundle != null) {
            mToolbar.setTitle(mBundle.getString("title"));
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
