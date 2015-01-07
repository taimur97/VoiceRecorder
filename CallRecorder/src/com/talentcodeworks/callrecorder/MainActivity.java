package com.talentcodeworks.callrecorder;

import com.talentcodeworks.callrecorder.fragment.Tab1Fragment;
import com.talentcodeworks.callrecorder.fragment.Tab2Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private FragmentManager mFm;
	private Fragment mFragment;
	
	private ImageView img11, img12, img21, img22, img31, img32, img41, img42;
	
	View.OnClickListener mTabClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.tab1:
				refreshTab(1);
				mFragment = new Tab1Fragment();
				mFm.beginTransaction().replace(R.id.container, mFragment).commit();
				break;
			case R.id.tab2:
				refreshTab(2);
				mFragment = new Tab2Fragment();
				mFm.beginTransaction().replace(R.id.container, mFragment).commit();
				break;
			case R.id.tab3:
				refreshTab(3);
				break;
			case R.id.tab4:
				refreshTab(4);
				break;
			}
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mFm = getFragmentManager();
		
		findViewById(R.id.tab1).setOnClickListener(mTabClick);
		findViewById(R.id.tab2).setOnClickListener(mTabClick);
		findViewById(R.id.tab3).setOnClickListener(mTabClick);
		findViewById(R.id.tab4).setOnClickListener(mTabClick);
		img11 = (ImageView) findViewById(R.id.img11);
		img12 = (ImageView) findViewById(R.id.img12);
		img21 = (ImageView) findViewById(R.id.img21);
		img22 = (ImageView) findViewById(R.id.img22);
		img31 = (ImageView) findViewById(R.id.img31);
		img32 = (ImageView) findViewById(R.id.img32);
		img41 = (ImageView) findViewById(R.id.img41);
		img42 = (ImageView) findViewById(R.id.img42);
	}

	private void refreshTab(int index) {
		img11.setImageResource(R.drawable.call_hd_en);
		img12.setVisibility(View.INVISIBLE);
		img21.setImageResource(R.drawable.adress_hd_en);
		img22.setVisibility(View.INVISIBLE);
		img31.setImageResource(R.drawable.keypad_hd_en);
		img32.setVisibility(View.INVISIBLE);
		img41.setImageResource(R.drawable.cog_hd_en);
		img42.setVisibility(View.INVISIBLE);
		
		switch(index) {
		case 1:
			img11.setImageResource(R.drawable.call_hd_st);
			img12.setVisibility(View.VISIBLE);
			break;
		case 2:
			img21.setImageResource(R.drawable.adress_hd_st);
			img22.setVisibility(View.VISIBLE);
			break;
		case 3:
			img31.setImageResource(R.drawable.keypad_hd_st);
			img32.setVisibility(View.VISIBLE);
			break;
		case 4:
			img41.setImageResource(R.drawable.cog_hd_st);
			img42.setVisibility(View.VISIBLE);
			break;
		}
	}
}