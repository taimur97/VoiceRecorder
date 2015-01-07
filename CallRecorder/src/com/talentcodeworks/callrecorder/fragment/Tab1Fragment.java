package com.talentcodeworks.callrecorder.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.talentcodeworks.callrecorder.R;
import com.talentcodeworks.callrecorder.adapter.CallLogAdapter;
import com.talentcodeworks.callrecorder.dto.Callslog;

public class Tab1Fragment extends Fragment {
	
	private View mView;
	private ArrayList<Callslog> mCallLogList = new ArrayList<Callslog>();

	public Tab1Fragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = View.inflate(getActivity(), R.layout.fragment_tab2, null);
		
		StringBuffer sb = new StringBuffer();
        Cursor managedCursor = getActivity().managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details :");
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String strDate = new SimpleDateFormat("MM-dd HH:mm:ss").format(callDayTime);
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
            case CallLog.Calls.OUTGOING_TYPE:
                dir = "OUTGOING";
                break;

            case CallLog.Calls.INCOMING_TYPE:
                dir = "INCOMING";
                break;

            case CallLog.Calls.MISSED_TYPE:
                dir = "MISSED";
                break;
            }
            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                    + dir + " \nCall Date:--- " + callDayTime
                    + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
            
            Callslog calllog = new Callslog();
            calllog.type = dircode;
            calllog.name = phNumber;
            calllog.date = strDate;
            mCallLogList.add(calllog);
        }
        managedCursor.close();
        
        ListView list = (ListView) mView.findViewById(R.id.listView1);
		CallLogAdapter adapter = new CallLogAdapter(getActivity(), mCallLogList);
		list.setAdapter(adapter);
		
		return mView;
	}

}
