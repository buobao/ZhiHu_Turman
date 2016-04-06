package com.zhihu.turman.app.entity.basketball;

import com.zhihu.turman.app.entity.BaseEntity;

import java.util.List;

/**
 * Created by dqf on 2016/4/6.
 */
public class BasketballSports extends BaseEntity {
    public String title;
    public GameStatus statuslist;
    public List<GameDay> list;
    public List<Teams> teammatch;
}
