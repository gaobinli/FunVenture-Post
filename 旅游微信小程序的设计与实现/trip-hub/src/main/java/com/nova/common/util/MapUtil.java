package com.nova.common.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil extends HashMap<String, Object> {

    @Override
    public MapUtil put(String key, Object value) {
        super.put(key, value);
        return this;
    }


    public MapUtil putMap(Map<String,Object> params) {
        super.putAll(params);
        return this;
    }
}
