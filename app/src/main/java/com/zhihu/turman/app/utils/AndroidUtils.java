package com.zhihu.turman.app.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by dqf on 2016/4/6.
 */
public class AndroidUtils {
    private static WindowManager wm;

    public static int getScreenWidth(Context context){
        if (wm == null) {
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context){
        if (wm == null) {
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return wm.getDefaultDisplay().getHeight();
    }
}
