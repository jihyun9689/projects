package com.wowell.talboro2.utils.image;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.wowell.talboro2.utils.logger.LogManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class DecodeImage {
	final static String TAG = "DecodeImage";

	public static Bitmap decodeSampledBitmapFromResource(String filePath,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions


	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

		if(bitmap == null){
			LogManager.printLog(DecodeImage.class,"inJustDecodeBounds is null");
		}

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;

	    return BitmapFactory.decodeFile(filePath, options);
	}

	private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        	}
    	}
    	return inSampleSize;
	}

	public synchronized static Bitmap safeDecodeBitmapFile(String strFilePath) {
		try {
			File file = new File(strFilePath);
			if (file.exists() == false) {
				return null;
			}
			final Bitmap bitmap = BitmapFactory.decodeFile(strFilePath);
			int degree = getExifOrientation(strFilePath);

			return getRotatedBitmap(bitmap, degree);
		} catch (OutOfMemoryError ex) {
			ex.printStackTrace();

			return null;
		}
	}

	public static Bitmap getRotatedBitmap(Bitmap bitmap, int degrees){

		if(bitmap !=null){
			Matrix m = new Matrix();
			LogManager.printLog(DecodeImage.class,"bitmap.getWidth() : " + bitmap.getWidth() + "   bitmap.getHeight() " + bitmap.getHeight());
			m.setRotate(degrees, (float) bitmap.getWidth() / 2 , (float) bitmap.getHeight() / 2);
			try{
				Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);

		//		이미지를 중간맞춤하고 자를 때
		//		if(bitmap.getHeight() > bitmap.getWidth()){
		//			b2 = Bitmap.createBitmap(bitmap,0 ,(bitmap.getHeight() - bitmap.getWidth()) / 2,bitmap.getWidth() ,bitmap.getWidth(),m,false);
		//		}else{
		//			b2 = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - bitmap.getHeight()) / 2, 0, bitmap.getHeight(),bitmap.getHeight(),m,false);
		//		}

				if(bitmap != b2){
					bitmap.recycle();
					bitmap = b2;
				}
				LogManager.printLog(DecodeImage.class,"getRotatedBitmap");
			}catch (OutOfMemoryError e){
				LogManager.printLog(DecodeImage.class, e.getStackTrace().toString());
			}
		}else {
			LogManager.printError(DecodeImage.class,"bitmap is null in getRotatedBitmap");
		}
		return bitmap;

	}

	public static int getExifOrientation(String filepath){
		int degree = 0;
		ExifInterface exif = null;

		try{
			exif = new ExifInterface(filepath);
		}catch (IOException e){
			Log.e(TAG,"cannot read exif");
			e.printStackTrace();
		}

		if(exif != null){
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,  -1);

			if(orientation != -1){
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					LogManager.printLog(DecodeImage.class,"ORIENTATION_ROTATE_90");
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					LogManager.printLog(DecodeImage.class,"ORIENTATION_ROTATE_180");
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					LogManager.printLog(DecodeImage.class,"ORIENTATION_ROTATE_270");
					degree = 270;
					break;
				}
			}else{
				LogManager.printLog(DecodeImage.class,"ORIENTATION_ROTATE_0");

			}
		}

		return degree;
	}
}

