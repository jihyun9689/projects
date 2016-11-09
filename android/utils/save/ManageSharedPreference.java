package com.wowell.talboro2.utils.save;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kim on 2016-01-21.
 */
public class ManageSharedPreference {
    private Context context;

    public ManageSharedPreference(Context context) {
        this.context = context;
    }

    public <T> void  putValue(String key, T value, Class<T> type){
        SharedPreferences sharedPreferences = context.getSharedPreferences("pre",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        putValue(editor, key, value, type);
    }

    public <T> T  getValue(String key, T defaultValue, Class<T> type){
        SharedPreferences sharedPreferences = context.getSharedPreferences("pre", Activity.MODE_PRIVATE);
        return getValue(sharedPreferences, key, defaultValue, type);
    }

    public void deleteValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("pre", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    private <T> void putValue(SharedPreferences.Editor editor, String key, T value, Class<T> type) {

        if (type == Boolean.class) {
            editor.putBoolean(key, (Boolean) value);
        } else if (type == Float.class) {
            editor.putFloat(key, (Float) value);
        } else if (type == Integer.class) {
            editor.putInt(key, (Integer) value);
        } else if (type == Long.class) {
            editor.putLong(key, (Long) value);
        } else if (type == String.class) {
            editor.putString(key, (String) value);
        }
        editor.commit();
    }

    private <T> T getValue(SharedPreferences sharedPreferences, String key, T defaultValue, Class<T> type) {

        T result = null;
        if (type == Boolean.class) {
            result = (T) getBoolean(sharedPreferences, key, (Boolean) defaultValue);
        } else if (type == Float.class) {
            result = (T) getFloat(sharedPreferences, key, (Float) defaultValue);
        } else if (type == Integer.class) {
            result = (T) getInt(sharedPreferences, key, (Integer) defaultValue);
        } else if (type == Long.class) {
            result = (T) getLong(sharedPreferences, key, (Long) defaultValue);
        } else if (type == String.class) {
            result = (T) sharedPreferences.getString(key, (String) defaultValue);
        }
        return result;
    }

    private Boolean getBoolean(SharedPreferences sharedPreferences, String key, Boolean defValue) {
        if (!sharedPreferences.contains(key)) {
            return defValue;
        }
        return sharedPreferences.getBoolean(key, false);
    }

    private Float getFloat(SharedPreferences sharedPreferences, String key, Float defValue) {
        if (!sharedPreferences.contains(key)) {
            return defValue;
        }
        return sharedPreferences.getFloat(key, 0);
    }

    private Integer getInt(SharedPreferences sharedPreferences, String key, Integer defValue) {
        if (!sharedPreferences.contains(key)) {
            return defValue;
        }
        return sharedPreferences.getInt(key, 0);
    }

    private Long getLong(SharedPreferences sharedPreferences, String key, Long defValue) {
        if (!sharedPreferences.contains(key)) {
            return defValue;
        }
        return sharedPreferences.getLong(key, 0);
    }


}
