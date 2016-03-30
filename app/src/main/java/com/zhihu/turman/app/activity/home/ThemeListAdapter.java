package com.zhihu.turman.app.activity.home;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhihu.turman.app.R;
import com.zhihu.turman.app.base.ListAdapter;
import com.zhihu.turman.app.entity.Theme;
import com.zhihu.turman.app.utils.CropCircleTransformation;

import java.util.LinkedList;

/**
 * Created by dqf on 2016/3/30.
 */
public class ThemeListAdapter extends ListAdapter<Theme> {
    public ThemeListAdapter(LinkedList<Theme> data, Context context) {
        super(data, context);
    }

    @Override
    protected void initView(View view, int position) {
        super.initView(view, position);
        holder.title.setText(mDataList.get(position).name);
        holder.content.setText(mDataList.get(position).description);
        Glide.with(mContext)
                .load(mDataList.get(position).thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_circle_placeholder)
                .error(R.drawable.ic_default_avatar)
                .centerCrop()
                .transform(new CropCircleTransformation(mContext))
                .into(holder.photo);
        holder.vote.setVisibility(View.GONE);
    }
}
