package com.example.caprin.zhihuibeijing.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Trace;

/**
 * Created by WD on 2016/10/14.
 */
public class PrefUtils {

    private static final String PREF_NAME = "config";

    public static boolean getBoolean(Context ctx, String key, boolean defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String s = sp.getString(key, value);
        return s;
    }

    public static void setString(Context ctx, String key, String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, defaultValue).commit();
    }
}
