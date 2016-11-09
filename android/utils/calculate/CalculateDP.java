package com.wowell.talboro2.utils.calculate;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.wowell.talboro2.utils.logger.LogManager;

/**
 * Created by kim on 2016-06-08.
 */
public class CalculateDP {

    public static int dpToPixel(int dp, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static void checkDPI(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager mgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mgr.getDefaultDisplay().getMetrics(metrics);

        LogManager.printLog(CalculateDP.class, "densityDPI = " + metrics.densityDpi);
    }
}
