package com.zhihu.turman.app.entity;

/**
 * Created by dqf on 2016/3/29.
 */
public class Articale extends BaseEntity {
    public String date; //发表日期
    public String name; //文章名称
    public String pic;  //抬头图url
    public int publishtime; //发表时间戳
    public int count; //评论数
    public String excerpt; //摘要
}
