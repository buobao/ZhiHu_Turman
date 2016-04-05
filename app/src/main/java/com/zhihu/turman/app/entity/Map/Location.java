package com.zhihu.turman.app.entity.Map;

import com.zhihu.turman.app.entity.BaseEntity;

import java.util.List;

/**
 * Created by dqf on 2016/4/5.
 */
public class Location extends BaseEntity {
    public List<LocationName> address_components;
    public String formatted_address;
    public Geometry geometry;
    public String place_id;
    public List<String> types;
}
