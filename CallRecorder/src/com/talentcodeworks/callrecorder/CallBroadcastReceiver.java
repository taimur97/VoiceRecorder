package com.talentcodeworks.callrecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import android.util.Log;

public class CallBroadcastReceiver extends BroadcastReceiver
{
    private Object mNumber;

	public void onReceive(Context context, Intent intent) {
        Log.d("LDK", "CallBroadcastReceiver");
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String numberToCall = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d("LDK", "CallBroadcastReceiver intent has EXTRA_PHONE_NUMBER: " + numberToCall);
        }
        
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE); //전화벨
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
        	PhoneListener phoneListener = new PhoneListener(context);
            TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            Log.d("LDK", "set PhoneStateListener");
        } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) { //통화 시작
        	
        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) { //통화 종료

        }

/*        PhoneListener phoneListener = new PhoneListener(context);
        TelephonyManager telephony = (TelephonyManager)
            context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        Log.d("PhoneStateReceiver::onReceive", "set PhoneStateListener");*/
    }
}
