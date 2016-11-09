package com.wowell.talboro2.utils.timer;

import android.os.Handler;
import android.os.Message;

import com.wowell.talboro2.utils.logger.LogManager;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kim on 2016-04-19.
 */
public class TimeCounter {
    TimeHandler timeHandler;
    Timer timer;
    TimerTask timerTask;
    OnTimeListener onTimeListener;
    long startTimeLong;

    long preMinuteTime;
    public TimeCounter(long startTimeLong) {
        this.startTimeLong = startTimeLong;
        timeHandler = new TimeHandler(startTimeLong);

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timeHandler.sendEmptyMessage(0);
            }
        };
    }

    public void setOnTimeListener(OnTimeListener onTimeListener){
        this.onTimeListener = onTimeListener;
    };

    private class TimeHandler extends Handler {
        long starTime;

        public TimeHandler(long starTime){
            this.starTime = starTime;
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                Calendar calendar = Calendar.getInstance();
                long second = (calendar.getTimeInMillis() - starTime) / 1000;
                long minuteTime = (second / 60) % 60;
                long hourTime = (second / 60 / 60) % 24;
                long dayTime = (second / 60 / 60 / 24);

                onTimeListener.onTime(dayTime, hourTime, minuteTime, second);

            }
        }
    }

    public String getCountTime(){
        String timeStr = null;

        Calendar calendar = Calendar.getInstance();
        long totlalSecond = (calendar.getTimeInMillis() - startTimeLong) / 1000;
        long second = totlalSecond % 60;
        long minuteTime = (totlalSecond / 60) % 60;
        long hourTime = (totlalSecond / 60 / 60) % 24;
        long dayTime = (totlalSecond / 60 / 60 / 24);

        if (dayTime != 0) {
            timeStr = dayTime + "일차";
        } else if (hourTime != 0) {
            timeStr = hourTime + "시간차";
        } else if (minuteTime != 0) {
            timeStr = minuteTime + "분차";
        } else {
            timeStr = "방금 전";
        }

        return timeStr;
    }

    public String getCountTimeForBoard(){
        String timeStr = null;

        Calendar calendar = Calendar.getInstance();
        long totlalSecond = (calendar.getTimeInMillis() - startTimeLong) / 1000;
        long second = totlalSecond % 60;
        long minuteTime = (totlalSecond / 60) % 60;
        long hourTime = (totlalSecond / 60 / 60) % 24;
        long dayTime = (totlalSecond / 60 / 60 / 24);

        if (dayTime != 0) {
            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTimeInMillis(startTimeLong);
            timeStr =
                    timeCalendar.get(Calendar.YEAR) +"년"+
                    String.format("%02d", timeCalendar.get(Calendar.MONTH) + 1) +"월" +
                    String.format("%02d", timeCalendar.get(Calendar.DAY_OF_MONTH)) + "일";
        } else if (hourTime != 0) {
            timeStr = hourTime + "시간 전";
        } else if (minuteTime != 0) {
            timeStr = minuteTime + "분 전";
        } else {
            timeStr = "방금 전";
        }

        return timeStr;
    }

    public String getCountTimeForComment(){
        String timeStr = null;

        Calendar calendar = Calendar.getInstance();
        long totlalSecond = (calendar.getTimeInMillis() - startTimeLong) / 1000;
        long second = totlalSecond % 60;
        long minuteTime = (totlalSecond / 60) % 60;
        long hourTime = (totlalSecond / 60 / 60) % 24;
        long dayTime = (totlalSecond / 60 / 60 / 24);

        if (dayTime != 0) {
            timeStr = dayTime + "일 전";
        } else if (hourTime != 0) {
            timeStr = hourTime + "시간 전";
        } else if (minuteTime != 0) {
            timeStr = minuteTime + "분 전";
        } else {
            timeStr = "방금 전";
        }

        return timeStr;
    }

    public void setListener(OnTimeListener onTimeListener){

        Calendar calendar = Calendar.getInstance();
        long totlalSecond = (calendar.getTimeInMillis() - startTimeLong) / 1000;
        long second = totlalSecond % 60;
        long minuteTime = (totlalSecond / 60) % 60;
        long hourTime = (totlalSecond / 60 / 60) % 24;
        long dayTime = (totlalSecond / 60 / 60 / 24);

        onTimeListener.onTime(dayTime, hourTime, minuteTime, second);
    }

    public void startCount(){
        timer.schedule(timerTask, 0, 1000);
    }
    public void stopCount(){
        timer.cancel();
    }

}
