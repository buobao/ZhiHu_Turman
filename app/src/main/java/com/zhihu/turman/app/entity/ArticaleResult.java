package com.zhihu.turman.app.entity;

import java.util.List;

/**
 * Created by dqf on 2016/3/29.
 */
public class ArticaleResult extends Result {
    public int count;  //本次获取文章数量
    public List<Articale> posts;  //文章列表
}
