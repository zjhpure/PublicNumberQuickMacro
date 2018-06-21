package org.pure.quickmacro.network.request;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import org.pure.quickmacro.util.JsonUtil;

public class NetRqtRequest<T> {
    private static final String LOG_TAG = "NetRqtRequest";
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, String> getFieldMap() {
        Map<String, String> map = new HashMap<>();
        String dataJsonStr = JsonUtil.objectToJson(data);
        map.put("data", dataJsonStr);
        Log.i(LOG_TAG, dataJsonStr);
        return map;
    }
}