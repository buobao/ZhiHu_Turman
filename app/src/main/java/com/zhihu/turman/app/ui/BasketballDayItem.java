package com.zhihu.turman.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.basketball.GameDay;
import com.zhihu.turman.app.entity.basketball.GameItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dqf on 2016/4/6.
 */
public class BasketballDayItem extends LinearLayout {

    private Context mContext;
    @Bind(R.id.day_title)
    protected TextView mTitle;
    @Bind(R.id.basketball_items)
    protected LinearLayout mLayout;

    public BasketballDayItem(Context context) {
        super(context);
        init(context);
    }

    public BasketballDayItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        View view = inflate(context, R.layout.item_list_basketball_day, null);
        ButterKnife.bind(this, view);
        setOrientation(VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,5,5,15);
        view.setLayoutParams(params);
        addView(view);
    }

    public void setData(GameDay gameDay){
        mTitle.setText(gameDay.title);
        mLayout.removeAllViews();
        for (GameItem item:gameDay.tr) {
            BasketballGameItem bItem = new BasketballGameItem(mContext);
            bItem.setData(item);
            mLayout.addView(bItem);
        }
    }
}


































