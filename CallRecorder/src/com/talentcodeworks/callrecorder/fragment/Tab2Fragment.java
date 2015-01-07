package com.talentcodeworks.callrecorder.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.talentcodeworks.callrecorder.R;
import com.talentcodeworks.callrecorder.adapter.AddressAdapter;
import com.talentcodeworks.callrecorder.dto.Address;

public class Tab2Fragment extends Fragment {

	private View mView;
	
	private ArrayList<Address> mAddressList = new ArrayList<Address>();

	public Tab2Fragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = View.inflate(getActivity(), R.layout.fragment_tab2, null);

/*		String[] columns = { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.HAS_PHONE_NUMBER };
		Cursor cursor = getActivity().getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, columns, null, null,
				null);

		int ColumeIndex_ID = cursor
				.getColumnIndex(ContactsContract.Contacts._ID);
		int ColumeIndex_DISPLAY_NAME = cursor
				.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
		int ColumeIndex_HAS_PHONE_NUMBER = cursor
				.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

		while (cursor.moveToNext()) {
			String id = cursor.getString(ColumeIndex_ID);
			String name = cursor.getString(ColumeIndex_DISPLAY_NAME);
			String has_phone = cursor.getString(ColumeIndex_HAS_PHONE_NUMBER);

			if (!has_phone.endsWith("0")) {
				Address address = new Address();
				address.name = name;
				address.phone_number = GetPhoneNumber(id);;
				System.out.println(name);
				mAddressList.add(address);
			}
		}
		cursor.close();*/
		
		ContentResolver cr = getActivity().getContentResolver();
	    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
	            null, null, null);
	    if (cur.getCount() > 0) {
	        while (cur.moveToNext()) {
	            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
	            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	            Address address = new Address();

	            Log.i("Names", name);
	            address.name = name;
	            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) 
	            {
	                // Query phone here. Covered next
	                Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null); 
	                while (phones.moveToNext()) { 
	                         String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	                         Log.i("Number", phoneNumber);
	                         address.phone_number = phoneNumber;
	                        } 
	                phones.close(); 
	            }
	            
	            mAddressList.add(address);
	        }
	    }
		
		ListView list = (ListView) mView.findViewById(R.id.listView1);
		AddressAdapter adapter = new AddressAdapter(getActivity(), mAddressList);
		list.setAdapter(adapter);

		return mView;
	}

	public String GetPhoneNumber(String id) {
		String number = "";
		Cursor phones = getActivity().getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone._ID + " = " + id, null,
				null);

		if (phones.getCount() > 0) {
			while (phones.moveToNext()) {
				number = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			}
			System.out.println(number);
		}

		phones.close();

		return number;
	}
}
