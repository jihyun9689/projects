package com.wowell.talboro2.utils.http;

/**
 * Created by kim on 2016-06-18.
 */
public interface NetworkResponseListener {
    public void successed(int responseCode, String response);
    public void failed(int responseCode, String response);
}
