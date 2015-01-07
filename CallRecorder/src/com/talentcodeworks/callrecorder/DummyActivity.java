package com.talentcodeworks.callrecorder;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class DummyActivity extends Activity {
	
	private static final int REQUEST_CODE = 1234;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
//		if (isConnected()) {
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			startActivityForResult(intent, REQUEST_CODE);
//		} else {
//			Toast.makeText(getApplicationContext(),
//					"Plese Connect to Internet", Toast.LENGTH_LONG)
//					.show();
//		}
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
			ArrayList<String> mStringList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			
			StringBuilder sb = new StringBuilder();
			for(String str : mStringList) {
				sb.append(str);
			}
			
			Toast.makeText(this, sb, Toast.LENGTH_LONG).show();
		}
		
		finish();
		
		super.onActivityResult(requestCode, resultCode, data);
	}
}
