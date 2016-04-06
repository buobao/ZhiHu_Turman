package com.zhihu.turman.app.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.basketball.GameItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dqf on 2016/4/6.
 */
public class BasketballGameItem extends LinearLayout implements View.OnClickListener {
    private Context mContext;

    @Bind(R.id.player1)
    protected TextView mPlayer1;
    @Bind(R.id.logo1)
    protected ImageView mLogo1;
    @Bind(R.id.score)
    protected TextView mScore;
    @Bind(R.id.status)
    protected TextView mStatus;
    @Bind(R.id.logo2)
    protected ImageView mLogo2;
    @Bind(R.id.player2)
    protected TextView mPlayer2;
    @Bind(R.id.web1)
    protected TextView mWeb1;
    @Bind(R.id.web2)
    protected TextView mWeb2;
    @Bind(R.id.game_time)
    protected TextView mTime;

    private String mUrl1 = "";
    private String mUrl2 = "";

    public BasketballGameItem(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public BasketballGameItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.item_list_basketball,null);
        ButterKnife.bind(this, view);

        setOrientation(VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 10);
        view.setLayoutParams(params);
        addView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web1:
                if (!"".equals(mUrl1)) {
                    Uri uri = Uri.parse(mUrl1);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(it);
                }
                break;
            case R.id.web2:
                if (!"".equals(mUrl2)) {
                    Uri uri1 = Uri.parse(mUrl2);
                    Intent it1 = new Intent(Intent.ACTION_VIEW, uri1);
                    mContext.startActivity(it1);
                }
                break;
        }

    }

    public void setData(GameItem item) {
        mPlayer1.setText(item.player1);
        Glide.with(mContext)
                .load(item.player1logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_circle_placeholder)
                .error(R.drawable.game_default)
                .centerCrop()
                .into(mLogo1);
        mScore.setText(item.score);

        if (item.status != 0) {
            mWeb1.setOnClickListener(this);
            mWeb1.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        if (item.status == 2) {
            mWeb2.setOnClickListener(this);
            mWeb2.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }

        mStatus.setText(item.status == 0 ? "未开赛" : item.status == 1 ? "直播中" : "已结束");
        Glide.with(mContext)
                .load(item.player2logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_circle_placeholder)
                .error(R.drawable.game_default)
                .centerCrop()
                .into(mLogo2);
        mPlayer2.setText(item.player2);

        String t = item.time.split(" ")[1];
        mTime.setText("开赛时间"+t);
        mUrl1 = item.link2url;
        mUrl2 = item.link1url;
    }
}
