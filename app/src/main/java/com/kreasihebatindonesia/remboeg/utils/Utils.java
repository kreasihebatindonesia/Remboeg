package com.kreasihebatindonesia.remboeg.utils;

import org.json.JSONObject;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class Utils {

    public static String optString(JSONObject json, String key) {
        return json.isNull(key) ? null : json.optString(key);
    }

    public static int optInt(JSONObject json, String key) {
        return json.isNull(key) ? 0 : json.optInt(key);
    }

    public static double optDouble(JSONObject json, String key) {
        return json.isNull(key) ? 0 : json.optDouble(key);
    }
}
