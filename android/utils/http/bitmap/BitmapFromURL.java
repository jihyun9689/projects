//package com.wowell.talboro2.utils.http.bitmap;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//
//import com.wowell.talboro2.utils.image.CacheBitmap;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//
///**
// * Created by kim on 2016-07-13.
// */
//public class BitmapFromURL extends AsyncTask<String, Void, Bitmap> {
//    String key;
//    DownLoadBitmapListener downLoadBitmapListener;
//    ArrayList<String> keys;
//
//    public BitmapFromURL(String key, DownLoadBitmapListener downLoadBitmapListener) {
//        this.key = key;
//        this.downLoadBitmapListener = downLoadBitmapListener;
//    }
//
//    public BitmapFromURL(ArrayList<String> keys, DownLoadBitmapListener downLoadBitmapListener) {
//        this.keys = keys;
//        this.downLoadBitmapListener = downLoadBitmapListener;
//    }
//
//    @Override
//    protected Bitmap doInBackground(String... params) {
//        return getBitmapFromURL(params[0]);
//    }
//
//    @Override
//    protected void onPostExecute(Bitmap bitmap) {
//        if(keys != null){
//            CacheBitmap.addBitmapToMaemoryCache(key, bitmap);
//            downLoadBitmapListener.onSuccess();
//        }
//        CacheBitmap.addBitmapToMaemoryCache(key, bitmap);
//        downLoadBitmapListener.onSuccess();
//    }
//
//    public static Bitmap getBitmap(String key){
//        return CacheBitmap.getBitmapFromMemoryCache(key);
//    }
//
//    private Bitmap getBitmapFromURL(String src) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            downLoadBitmapListener.onFail();
//            return null;
//        }
//    }
//
//}
