package com.zhihu.turman.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.zhihu.turman.app.activity.common.CommonActivity;

/**
 * Created by dqf on 2016/3/30.
 */
public class TurmanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 打开common activity
     * @param activity
     * @param bundle
     */
    public void openCommon(Activity activity, Bundle bundle){
        Intent intent = new Intent(activity, CommonActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

}
