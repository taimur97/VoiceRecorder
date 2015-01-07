package com.talentcodeworks.callrecorder;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceActivity extends Activity {

	private static final String TAG = "LDK";
	private static final int REQUEST_CODE = 1234;

	private ImageView ivProfile;
	private TextView tvName, tvNumber;

	private TextView tvResult;

	private boolean mIsStop = false;
	private String mNumber = " ";
	private String mContactName = " ";
	
	private String mText = "";
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case 0:
				callVoiceReconition();
				break;
			}
		}
		
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_voice);

		ivProfile = (ImageView) findViewById(R.id.ivProfile);
		tvName = (TextView) findViewById(R.id.tvName);
		tvNumber = (TextView) findViewById(R.id.tvNumber);
		tvResult = (TextView) findViewById(R.id.tvResult);

		//getId("01034648576");

		mIsStop = getIntent().getBooleanExtra("stop", false);
		mNumber = getIntent().getStringExtra("number");
		getId(mNumber);
		
		Log.d("LDK", "number:" + mNumber);

		callVoiceReconition();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d("LDK", "onNewIntent");
		mIsStop = intent.getBooleanExtra("stop", false);
		super.onNewIntent(intent);
	}

	private void callVoiceReconition() {
		if (isConnected()) {
			Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);            //intent 생성
			i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());    //호출한 패키지
			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");                            //음성인식 언어 설정
			i.putExtra(RecognizerIntent.EXTRA_PROMPT, "");                     //사용자에게 보여 줄 글자

			startActivityForResult(i, REQUEST_CODE);                                                //구글 음성인식 실행
		}
		else {
			Toast.makeText(getApplicationContext(),
			"Plese Connect to Internet", Toast.LENGTH_LONG)
			.show();
		}

	}

	public boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = cm.getActiveNetworkInfo();
		if (net != null && net.isAvailable() && net.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	
	

	@Override
	protected void onDestroy() {
		mHandler.removeMessages(0);
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("LDK", "requestCode:" + requestCode + " resultCode:" + resultCode);
		
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			ArrayList<String> mStringList = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			
			mText += mStringList.get(0) + " ";
			
			// Toast.makeText(this, sb, Toast.LENGTH_LONG).show();
			tvResult.setText(mText);
		}
		
		if (!mIsStop) {
			mHandler.sendEmptyMessageDelayed(0, 500);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void getId(String phoneNumber) {
		try {
			ContentResolver contentResolver = getContentResolver();
	
			Uri uri = Uri.withAppendedPath(
					ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
					Uri.encode(phoneNumber));
	
			String[] projection = new String[] {
					ContactsContract.PhoneLookup.DISPLAY_NAME,
					ContactsContract.PhoneLookup._ID };
	
			Cursor cursor = contentResolver
					.query(uri, projection, null, null, null);
	
			if (cursor != null) {
				while (cursor.moveToNext()) {
					mContactName = cursor
							.getString(cursor
									.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
					String contactId = cursor
							.getString(cursor
									.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
					Log.d(TAG, "contactMatch name: " + mContactName);
					Log.d(TAG, "contactMatch id: " + contactId);
				}
				cursor.close();
			}
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		tvName.setText(mContactName);
		tvNumber.setText(mNumber);
	}
}
