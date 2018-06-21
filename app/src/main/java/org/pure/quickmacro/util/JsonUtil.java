package org.pure.quickmacro.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json工具
 */
public class JsonUtil {
    public static String objectToJson(Object object) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(object);
    }
}