package com.zhihu.turman.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhihu.turman.app.R;

import java.util.Map;

/**
 * Created by dqf on 2016/3/11.
 */
public class SearchBar extends LinearLayout implements View.OnClickListener {
    public static final String SEARCH_PROPERTY = "pageProperty";
    public static final String SEARCH_KEYWORD = "pageKeyword";

    private EditText mSearchContext;
    private TextView mSearchBtn;
    private Context context;

    private int mBackgroundColor=-1;

    //其他附加的条件
    private Map<String,Object> params;

    public SearchBar(Context context) {
        super(context);
        init(context);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.view_searchbar,null);
        mSearchContext = (EditText) view.findViewById(R.id.frg_list_search_input);
        mSearchBtn = (TextView) view.findViewById(R.id.frg_list_search_btn);
        mSearchBtn.setOnClickListener(this);
        setOrientation(VERTICAL);

        if (mBackgroundColor != -1){
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.frg_list_search_layout);
            layout.setBackgroundColor(mBackgroundColor);
        }
        addView(view);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public void onClick(View v) {
        //这里写查询过程
        if (searchOption != null) {
            searchOption.search();
        }
    }

    public String getSearchString(){
        return mSearchContext.getText().toString();
    }

    private SearchOption searchOption;

    public interface SearchOption{
        public void search();
    }

    public SearchOption getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(SearchOption searchOption) {
        this.searchOption = searchOption;
    }

    public int getmBackgroundColor() {
        return mBackgroundColor;
    }

    public void setmBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
    }

    /**
     * 设置查询提示字符
     * @param hint
     */
    public void setSearchHint(int hint){
        mSearchContext.setHint(hint);
    }
}























