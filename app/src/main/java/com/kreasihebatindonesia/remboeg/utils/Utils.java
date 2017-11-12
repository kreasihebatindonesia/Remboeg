package com.kreasihebatindonesia.remboeg.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

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

    public static void setDrawableTint(Context context, Drawable drawable, int color){
        int newColor = ContextCompat.getColor(context, color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(drawable, color);
        } else {
            drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }
}
