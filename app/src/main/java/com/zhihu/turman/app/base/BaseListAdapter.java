package com.zhihu.turman.app.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhihu.turman.app.R;
import com.zhihu.turman.app.entity.BaseEntity;

import java.util.List;

/**
 * Created by dqf on 2016/3/30.
 */
public abstract class BaseListAdapter<T extends BaseEntity> extends RecyclerView.Adapter<BaseListAdapter.ViewHolder> {

    public static final int ITEM_LAYOUT_NORMAL = 0;
    public static final int ITEM_LAYOUT_SIMPLE = 1;

    protected List<T> mList;
    protected Context mContext;
    private int mType;

    public int getType(){
        return ITEM_LAYOUT_NORMAL;
    }

    public BaseListAdapter(Context context, List<T> list){
        super();
        mContext = context;
        mList = list;
        mType = getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (mType){
            case ITEM_LAYOUT_NORMAL:
                layout = R.layout.item_list_normal;
                break;
            case ITEM_LAYOUT_SIMPLE:
                layout = R.layout.item_list_simple;
                break;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;

        public TextView vote;
        public ImageView photo;
        public TextView content;
        public ImageView createrIcon;
        public TextView creater;
        public TextView date;

        public ImageView[] image = new ImageView[5];

        public ViewHolder(View itemView) {
            super(itemView);

            if (mType == ITEM_LAYOUT_NORMAL) {
                title = (TextView) itemView.findViewById(R.id.item_title);
                vote = (TextView) itemView.findViewById(R.id.item_vote);
                photo = (ImageView) itemView.findViewById(R.id.item_photo);
                content = (TextView) itemView.findViewById(R.id.item_content);
                createrIcon = (ImageView) itemView.findViewById(R.id.item_user_photo);
                creater = (TextView) itemView.findViewById(R.id.item_creater);
                date = (TextView) itemView.findViewById(R.id.item_date);
            }

            if (mType == ITEM_LAYOUT_SIMPLE) {
                title = (TextView) itemView.findViewById(R.id.item_title);
                image[0] = (ImageView) itemView.findViewById(R.id.item_image1);
                image[1] = (ImageView) itemView.findViewById(R.id.item_image2);
                image[2] = (ImageView) itemView.findViewById(R.id.item_image3);
                image[3] = (ImageView) itemView.findViewById(R.id.item_image4);
                image[4] = (ImageView) itemView.findViewById(R.id.item_image5);
            }
        }
    }
}



























