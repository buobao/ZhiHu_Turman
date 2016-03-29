package com.zhihu.turman.app.activity.home;

import android.content.Context;
import android.view.View;

import com.zhihu.turman.app.base.ListAdapter;
import com.zhihu.turman.app.entity.Articale;

import java.util.LinkedList;

/**
 * Created by dqf on 2016/3/29.
 */
public class ArticaleListAdapter extends ListAdapter<Articale> {
    public ArticaleListAdapter(LinkedList<Articale> data, Context context) {
        super(data, context);
    }

    @Override
    protected void initView(View view, int position) {
        super.initView(view, position);
        String title = mDataList.get(position).name;
        if (title.length() > 15) {
            String tmp = title.substring(15);
            title = title.substring(0,15) + "\n";
            if (tmp.length() < 15) {
                title += tmp;
            } else {
                title += tmp.substring(0,12) + "...";
            }
        }
        holder.title.setText(title);
        int count = mDataList.get(position).count;
        if (count > 999) {
            holder.vote.setText("999+");
        } else {
            holder.vote.setText(count+"");
        }

        holder.content.setText(mDataList.get(position).excerpt);
//        holder.photo.setImageDrawable();
//        holder.createrIcon.setImageDrawable();
//        holder.creater.setText(mDataList.get(position).);
//        holder.date.setText("2016-03-13");
    }
}
