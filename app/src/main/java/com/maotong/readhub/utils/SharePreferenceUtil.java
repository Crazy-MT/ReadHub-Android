package com.maotong.readhub.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.maotong.readhub.R;


public class SharePreferenceUtil {

    private SharePreferenceUtil() {}

    public static final String SHARED_PREFERENCE_NAME = "readhub";
    public static final String IMAGE_DESCRIPTION = "image_description";
    public static final String VIBRANT = "vibrant";
    public static final String MUTED = "muted";
    public static final String IMAGE_GET_TIME = "image_get_time";

    public static boolean isRefreshOnlyWifi(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.pre_refresh_data), false);
    }

    public static boolean isChangeThemeAuto(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.pre_get_image), true);
    }

    public static boolean isImmersiveMode(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.pre_status_bar), true);
    }

    public static boolean isChangeNavColor(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.pre_nav_color), true);
    }

    public static boolean isUseLocalBrowser(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.pre_use_local), false);
    }
}
