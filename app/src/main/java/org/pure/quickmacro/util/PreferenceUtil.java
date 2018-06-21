package org.pure.quickmacro.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import org.pure.quickmacro.MyApplication;

/**
 * Preference工具
 */
public class PreferenceUtil {
    private static SharedPreferences getDefaultPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
    }

    public static boolean putString(String key, String value) {
        SharedPreferences sp = getDefaultPreferences();
        Editor editor = sp.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(String key, String defValue) {
        SharedPreferences sp = getDefaultPreferences();
        return sp.getString(key, defValue);
    }
}
