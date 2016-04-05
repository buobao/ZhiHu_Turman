package com.zhihu.turman.app.entity;

import com.zhihu.turman.app.entity.Map.Location;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dqf on 2016/4/5.
 */
public class MapResult implements Serializable {

    public String status;
    public List<Location> results;
}
