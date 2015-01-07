package com.talentcodeworks.callrecorder.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.talentcodeworks.callrecorder.R;
import com.talentcodeworks.callrecorder.dto.Address;

public class AddressAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<Address> mAddressList;
	
	public AddressAdapter(Context context, List<Address> baseballList) {
		mContext = context;
		mAddressList = baseballList;
	}

	@Override
	public int getCount() {
		return mAddressList.size();
	}

	@Override
	public Object getItem(int position) {
		return mAddressList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = View.inflate(mContext, R.layout.list_tab2, null);
		}
		
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvPhoneNumber = (TextView) convertView.findViewById(R.id.tvPhoneNumber);
		
		tvName.setText(mAddressList.get(position).name);
		tvPhoneNumber.setText(mAddressList.get(position).phone_number);
		
		return convertView;
	}

}
