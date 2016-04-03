package com.zhihu.turman.app.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/4/3.
 */
public class News extends BaseEntity {
    public String body;
    public String title;
    public String share_url;
    public List<String> js;
    public Theme theme;
    public String ga_prefix;
    public int type;
    public int id;
    public List<String> css;
}
