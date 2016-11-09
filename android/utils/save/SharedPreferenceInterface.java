package com.wowell.talboro2.utils.save;

import android.content.Context;

/**
 * Created by kim on 2016-06-11.
 */
public interface SharedPreferenceInterface<T> {
    public void putValueSharedPreference(Context context);
    public void setValueSharedPreference(Context context);
    public void deleteValueSharedPreference(Context context);
}
