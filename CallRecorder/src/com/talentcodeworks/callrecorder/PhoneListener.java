package com.talentcodeworks.callrecorder;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneListener extends PhoneStateListener
{
    private Context context;
    
    private Handler mHandler = new Handler();
    
    private String mNumber = "";

    public PhoneListener(Context c) {
        Log.i("CallRecorder", "PhoneListener constructor");
        context = c;
    }

    public void onCallStateChanged (int state, final String incomingNumber)
    {
        Log.d("LDK", "PhoneListener::onCallStateChanged state:" + state + " incomingNumber:" + incomingNumber);

        switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
            Log.d("LDK", "CALL_STATE_IDLE, stoping recording");
            //Boolean stopped = context.stopService(new Intent(context, RecordService.class));
            //Log.i("CallRecorder", "stopService for RecordService returned " + stopped);
            Intent intent = new Intent(context, VoiceActivity.class);
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        intent.putExtra("stop", true);
	        context.startActivity(intent);
            break;
        case TelephonyManager.CALL_STATE_RINGING:
            Log.d("LDK", "CALL_STATE_RINGING");
            mNumber = incomingNumber;
            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
            Log.d("LDK", "CALL_STATE_OFFHOOK starting recording");
            /*Intent callIntent = new Intent(context, RecordService.class);
            ComponentName name = context.startService(callIntent);
            if (null == name) {
                Log.e("CallRecorder", "startService for RecordService returned null ComponentName");
            } else {
                Log.i("CallRecorder", "startService returned " + name.flattenToString());
            }*/
            
            //speaker on
            mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
		            AudioManager audioManager =  (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		            audioManager.setMode(AudioManager.MODE_IN_CALL);
		            audioManager.setSpeakerphoneOn(true);
				}
			}, 500);

            //voice reconition
            mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					 Intent intent = new Intent(context, VoiceActivity.class);
			         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			         intent.putExtra("number", mNumber);
			         context.startActivity(intent);
				}
			}, 1000);
           
            
            break;
        }
    }

}
