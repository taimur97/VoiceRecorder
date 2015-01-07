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
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class VoiceActivity extends Activity {

	private static final String TAG = "LDK";
	private static final int REQUEST_CODE = 1234;

	private ImageView ivProfile;
	private TextView tvName, tvNumber;

	private TextView tvResult;

	private boolean mIsStop = false;
	private String mNumber = "";

	StringBuilder sb = new StringBuilder();
	private String mContactName;

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

		if (mIsStop) {

		} else {
			callVoiceReconition();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		mIsStop = getIntent().getBooleanExtra("stop", false);
		super.onNewIntent(intent);
	}

	private void callVoiceReconition() {
		// if (isConnected()) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		startActivityForResult(intent, REQUEST_CODE);
		// } else {
		// Toast.makeText(getApplicationContext(),
		// "Plese Connect to Internet", Toast.LENGTH_LONG)
		// .show();
		// }
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			ArrayList<String> mStringList = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			for (String str : mStringList) {
				sb.append(str);
			}

			// Toast.makeText(this, sb, Toast.LENGTH_LONG).show();
			tvResult.setText(sb.toString());

			if (!mIsStop) {
				callVoiceReconition();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void getId(String phoneNumber) {
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
		
		tvName.setText(mContactName);
		tvNumber.setText(mNumber);
	}
}
