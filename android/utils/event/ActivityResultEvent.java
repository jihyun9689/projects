package com.wowell.talboro2.utils.event;

import android.content.Intent;

/**
 * Created by kim on 2016-06-15.
 */
public class ActivityResultEvent {

    private int requestCode;
    private int resultCode;
    private Intent data;

    public static ActivityResultEvent create(int requestCode, int resultCode, Intent intent) {
        return new ActivityResultEvent(requestCode, resultCode, intent);
    }

    private ActivityResultEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getData() {
        return data;
    }
}