package com.zhihu.turman.app.activity.common.fragments;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhihu.turman.app.R;
import com.zhihu.turman.app.base.BaseListAdapter;
import com.zhihu.turman.app.entity.Topic;
import com.zhihu.turman.app.utils.CropCircleTransformation;

import java.util.List;

/**
 * Created by dqf on 2016/3/30.
 */
public class TopicListAdapter extends BaseListAdapter<Topic> {

    public TopicListAdapter(Context context, List<Topic> list) {
        super(context, list);
    }

    @Override
    public int getType() {
        return ITEM_LAYOUT_SIMPLE;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Topic entity = mList.get(position);

        holder.title.setText(entity.title);

        int index = 0;
        if (entity.images != null && entity.images.size() > 0) {
            for (String url : entity.images) {
                if (index == 5) {
                    break;
                }
                Glide.with(mContext)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.img_circle_placeholder)
                        .error(R.drawable.default_photo)
                        .centerCrop()
                        .transform(new CropCircleTransformation(mContext))
                        .into(holder.image[index]);
                index++;
            }
        } else {
            for (int i=0;i<5;i++){
                holder.image[i].setVisibility(View.GONE);
            }
        }
    }
}




















