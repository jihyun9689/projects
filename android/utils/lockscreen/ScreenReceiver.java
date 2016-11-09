package com.wowell.talboro2.utils.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.wowell.talboro2.base.state.TaltalGCMState;
import com.wowell.talboro2.feature.lockscreen.LockScreenActivity;
import com.wowell.talboro2.sdk.GCM.UnregistrationIntentService;
import com.wowell.talboro2.utils.logger.LogManager;

/**
 * Created by kim on 2016-08-09.
 */
public class ScreenReceiver extends BroadcastReceiver {
    private TelephonyManager telephonyManager = null;
    private boolean isPhoneIdle = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_OFF) && TaltalGCMState.getInstance().getValue() != null
                || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) && TaltalGCMState.getInstance().getValue() != null) {
            LogManager.printLog(getClass(), "ACTION_SCREEN_OFF");
            if(telephonyManager == null){
                telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            }

            if(isPhoneIdle){
                Intent i = new Intent(context, LockScreenActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }else if(action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
            LogManager.printLog(getClass(), "ACTION_PACKAGE_REMOVED");
            Intent intent2 = new Intent(context, UnregistrationIntentService.class);
            context.startService(intent2);
        }
    }

    private PhoneStateListener phoneListener = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber){
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE :
                    isPhoneIdle = true;
                    break;
                case TelephonyManager.CALL_STATE_RINGING :
                    isPhoneIdle = false;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK :
                    isPhoneIdle = false;
                    break;
            }
        }
    };
}