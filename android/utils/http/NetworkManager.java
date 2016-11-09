package com.wowell.talboro2.utils.http;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wowell.talboro2.controller.request.RequestMethod;
import com.wowell.talboro2.model.Header;
import com.wowell.talboro2.utils.logger.LogManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kim on 2016-06-11.
 */
public class NetworkManager extends AsyncTask<Void, Void, Integer>{

    String urlString;
    String requestMethod;

    HeaderManager headerManager;
    JSONObject jsonObject;
    NetworkResponseListener networkResponseListener;
    String response;

    public NetworkManager(String urlString, String requestMethod, NetworkResponseListener networkResponseListener) {
        this.urlString = urlString;
        this.networkResponseListener = networkResponseListener;
        this.requestMethod = requestMethod;
    }
    public void addParams(String params){
        urlString = urlString + "?"+ params;
    }

    public void setHeader(HeaderManager headerManager){
        this.headerManager = headerManager;
    }

    public void setBody(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int responseCode = 0;
        try {
            LogManager.printLog(getClass(), "urlString : " + urlString + " requestMethod : " + requestMethod);
            URL url = new URL(urlString);

            HttpURLConnection conn    = null;
            OutputStream os   = null;
            InputStream is   = null;
            ByteArrayOutputStream baos = null;

            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(4000);
            conn.setReadTimeout(4000);
            conn.setRequestMethod(requestMethod);

            if(headerManager != null){
                for(Header header : headerManager.getHeaderArrayList()){
                    conn.setRequestProperty(header.getKey(), header.getValue());
                    LogManager.printLog(getClass(), "header : " + header.getKey() + " : " + header.getValue());
                }
            }else{
                conn.setRequestProperty("Content-Type", "application/json");
            }

            if(requestMethod != "GET" && requestMethod != "DELETE"){
                conn.setDoOutput(true);
                conn.setDoInput(true);
            }

            if(jsonObject != null){
                os = conn.getOutputStream();
                LogManager.printLog(getClass(), "jsonObject.toString() : " + jsonObject.toString());
                os.write(jsonObject.toString().getBytes());
                os.flush();
            }

            responseCode = conn.getResponseCode();

            LogManager.printLog(getClass(),"responseCode : " + responseCode);
            if(responseCode >= 200 && responseCode < 600) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData);

                LogManager.printLog(getClass(),response);
            }

        } catch (IOException e) {
            e.getStackTrace();
            LogManager.printError(getClass(), "IOException : " + e.getMessage());
            //networkResponseListener.failed(responseCode, response);
        }
        return responseCode;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer >= 200 && integer < 300) {
            LogManager.printLog(getClass(), "onPreExecute success");
            networkResponseListener.successed(integer, response);
        }else if(integer >= 400 && integer < 600){
            networkResponseListener.failed(integer, response);
            LogManager.printLog(getClass(), "connection fail : " + " error code : " + integer + " response : "+response);
        }else{
            LogManager.printLog(getClass(),"error");
        }
    }
}
