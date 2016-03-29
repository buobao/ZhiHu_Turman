package com.zhihu.turman.app.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.BaseEntity;
import com.zhihu.turman.app.entity.Holder;

import java.util.LinkedList;

/**
 * Created by dqf on 2016/3/29.
 */
public class ListAdapter<T extends BaseEntity> extends BaseAdapter {
    protected LinkedList<T> mDataList;
    protected Context mContext;
    protected Holder holder;

    public ListAdapter(LinkedList<T> data, Context context){
        mDataList = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(getItemLayout(),parent,false);
        initView(convertView, position);
        return convertView;
    }

    protected int getItemLayout(){
        return R.layout.item_list_normal;
    }

    protected void initView(View view, int position){
        //初始化item内容
        holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder();
            holder.title = (TextView) view.findViewById(R.id.item_title);
            holder.vote = (TextView) view.findViewById(R.id.item_vote);
            holder.photo = (ImageView) view.findViewById(R.id.item_photo);
            holder.content = (TextView) view.findViewById(R.id.item_content);
            holder.createrIcon = (ImageView) view.findViewById(R.id.item_user_photo);
            holder.creater = (TextView) view.findViewById(R.id.item_creater);
            holder.date = (TextView) view.findViewById(R.id.item_date);
            view.setTag(holder);
        }
    }
}
