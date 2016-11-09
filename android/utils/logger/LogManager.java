package com.wowell.talboro2.utils.logger;
import android.util.Log;

public class LogManager {
    private static final String _TAG = "Talboro2";

    public static final boolean _DEBUG = true;

    public static void printLog(Class activityName, String text){
    	if (_DEBUG){
            Log.d(_TAG, activityName.getName() + " : " + text);
    	}
    }

    public static void printError(Class activityName, String text){
    	if(true){
            Log.e(_TAG, activityName.getName() + " : " + text);
        }
    }
}
