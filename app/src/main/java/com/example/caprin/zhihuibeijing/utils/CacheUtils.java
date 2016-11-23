package com.example.caprin.zhihuibeijing.utils;

import android.content.Context;

/**
 * Created by caprin on 16-11-23.
 */
public class CacheUtils {

    public static void setCache(Context ctx, String key, String value) {
        PrefUtils.setString(ctx, key, value);
    }

    public static String getCache(Context ctx, String key) {
        return PrefUtils.getString(ctx, key, null);
    }
}
