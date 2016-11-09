package com.wowell.talboro2.utils.http;

import android.os.AsyncTask;

import com.wowell.talboro2.controller.TokenController;
import com.wowell.talboro2.model.Header;
import com.wowell.talboro2.utils.logger.LogManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by kim on 2016-06-11.
 */
public class NetworkMultipart extends AsyncTask<Void,Void,Integer>{
    String urlString;
    String fileName;
    byte[] byteArray;
    NetworkResponseListener networkResponseListener;
    String response;
    HeaderManager headerManager;
    String name;
    String requestMethod;
    String dataName;
    String data;

    public NetworkMultipart(String urlString, String requestMethod, NetworkResponseListener networkResponseListener) {
        this.urlString = urlString;
        this.requestMethod = requestMethod;
        this.networkResponseListener = networkResponseListener;
    }

    public void addFile(String name, String fileName, byte[] byteArray){
        LogManager.printLog(getClass(), "name : " + name);
        this.name = name;
        this.fileName = fileName;
        this.byteArray = byteArray;
    }

    public void addData(String dataName, String data){
        this.dataName = dataName;
        this.data = data;
    }

    public void setHeader(HeaderManager headerManager){
        this.headerManager = headerManager;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        InputStream is   = null;
        ByteArrayOutputStream baos = null;

        int responseCode = 0;
        try{
            URL url = new URL(urlString);
            LogManager.printLog(getClass(), "urlString : " + urlString + " method : " + "multipart/form " + requestMethod);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(1000 * 5);
            conn.setChunkedStreamingMode(1024 * 1000);
            conn.setRequestMethod(requestMethod);

            conn.setRequestProperty("Connection", "Keep-Alive");

            if(headerManager != null){
                for(Header header : headerManager.getHeaderArrayList()){
                    conn.setRequestProperty(header.getKey(), header.getValue());
                    LogManager.printLog(getClass(), "header : " + header.getKey() + " : " + header.getValue());
                }
            }else{
                conn.setRequestProperty("Content-Type", "application/json");
            }
            conn.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream request = new DataOutputStream(
                    conn.getOutputStream());

            if(name != null) {
                LogManager.printLog(getClass(), "Content-Disposition: form-data; name=\"" +
                        name + "\";filename=\"" +
                        fileName + "\"");
                request.writeBytes(twoHyphens + boundary + crlf);
                request.writeBytes("Content-Disposition: form-data; name=\"" +
                        name + "\"; filename=\"" +
                        fileName + "\"" + crlf);
                request.writeBytes("Content-Type: image/png" + crlf);
                request.writeBytes(crlf);
                request.write(byteArray);
                request.writeBytes(crlf);
            }

            if(dataName != null) {
                request.writeBytes(twoHyphens + boundary + crlf);
                request.writeBytes("Content-Disposition: form-data; name=\"" +
                        dataName + "\"" + crlf + crlf);
                request.write(data.getBytes("utf-8"));
                request.writeBytes(crlf);
            }
            request.writeBytes(twoHyphens + boundary +
                    twoHyphens + crlf);
            request.flush();
            request.close();

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
                //networkResponseListener.successed(responseCode, response);

                LogManager.printLog(getClass(),response);
            }

        }catch(Exception e){
            LogManager.printError(getClass(), e.getMessage());
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
            LogManager.printLog(getClass(), "connection fail" + " responseCode: " + integer + " response : " + response);
        }else{
            LogManager.printLog(getClass(),"connection ?");
        }
    }
}
