package com.zhihu.turman.app.entity.basketball;

import com.zhihu.turman.app.entity.BaseEntity;

import java.util.List;

/**
 * Created by dqf on 2016/4/6.
 */
public class GameDay extends BaseEntity {

    public String title;
    public List<GameItem> tr;
    public List<GameLive> live;
    public List<Links> livelink;
    public List<Links> bottomlink;
}
