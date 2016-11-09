//package com.wowell.talboro2.utils.image;
//
//import android.graphics.Bitmap;
//import android.util.LruCache;
//
///**
// * Created by kim on 2016-02-15.
// */
//public class CacheBitmap {
//
//    static final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//    static final int cacheSize = maxMemory / 8;
//
//    static LruCache<String,Bitmap> mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
//        @Override
//        protected int sizeOf(String key, Bitmap bitmap) {
//            return bitmap.getByteCount() / 1024;
//        }
//    };
//
//    public static void addBitmapToMaemoryCache(String key, Bitmap bitmap){
//        if(getBitmapFromMemoryCache(key) == null){
//            mMemoryCache.put(key,bitmap);
//        }
//    }
//
//    public static Bitmap getBitmapFromMemoryCache(String key){
//        return  mMemoryCache.get(key);
//    }
//}
//
//
