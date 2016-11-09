package com.wowell.talboro2.utils.date;

import com.wowell.talboro2.controller.MyInfoController;
import com.wowell.talboro2.utils.logger.LogManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kim on 2016-06-17.
 */
public class DateFunction {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public static String makePresentTimeString(){
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        String startTimeString = calendar.get(Calendar.YEAR) + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "_"
                + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + String.format("%02d", calendar.get(Calendar.MINUTE)) + String.format("%02d", calendar.get(Calendar.SECOND));

        LogManager.printLog(DateFunction.class, "makePresentTimeString : " + startTimeString);
        return startTimeString;
    }

    public static Date StringToDate(String dateString){
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Calendar StringToCalendar(String dateString){
        Date date = null;
        Calendar calendar = null;
        try {
            date = simpleDateFormat.parse(dateString);
            calendar = (Calendar) Calendar.getInstance().clone();
            calendar.setTime(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
