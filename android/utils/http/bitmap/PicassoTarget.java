//package com.wowell.talboro2.utils.http.bitmap;
//
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;
//import com.wowell.talboro2.utils.image.CacheBitmap;
//
///**
// * Created by kim on 2016-07-13.
// */
//public class PicassoTarget implements Target {
//    String key;
//    DownLoadBitmapListener downLoadBitmapListener;
//
//    public PicassoTarget(String key, DownLoadBitmapListener downLoadBitmapListener) {
//        this.key = key;
//        this.downLoadBitmapListener = downLoadBitmapListener;
//    }
//
//    @Override
//    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//        CacheBitmap.addBitmapToMaemoryCache(key, bitmap);
//        downLoadBitmapListener.onSuccess();
//    }
//
//    @Override
//    public void onBitmapFailed(Drawable errorDrawable) {
//        downLoadBitmapListener.onFail();
//    }
//
//    @Override
//    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//    }
//}
