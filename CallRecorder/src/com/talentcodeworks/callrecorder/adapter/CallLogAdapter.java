package com.talentcodeworks.callrecorder.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.talentcodeworks.callrecorder.R;
import com.talentcodeworks.callrecorder.dto.Callslog;

public class CallLogAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<Callslog> mAddressList;
	
	public CallLogAdapter(Context context, List<Callslog> baseballList) {
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
			convertView = View.inflate(mContext, R.layout.list_tab1, null);
		}
		
		ImageView imgType = (ImageView) convertView.findViewById(R.id.imgType);
		switch(mAddressList.get(position).type) {
		case 1:
			imgType.setImageResource(R.drawable.icon_call_in);
			break;
		case 2:
			imgType.setImageResource(R.drawable.icon_call_back);
			break;
		case 3:
			imgType.setImageResource(R.drawable.icon);
			break;
		}
		
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		tvName.setText(mAddressList.get(position).name);
		TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
		tvDate.setText(mAddressList.get(position).name);
		
		return convertView;
	}

}
