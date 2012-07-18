package com.aligo.idle.contacts;

import java.util.ArrayList;
import java.util.List;

import com.aligo.idle.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ContactsList extends Activity {

    private ContactsAdapter adapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        adapter = new ContactsAdapter(this, getContacts());
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
   
    private List<String> getContacts() {
    	List<String> contacts_name = new ArrayList<String>();
    	Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor cur =  managedQuery(uri, projection, null, null, sortOrder);
    	if (cur.getCount() > 0) {
    		while (cur.moveToNext()) {
    			String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    			contacts_name.add(name);
    		}
    	}
    	return contacts_name;
    }
    
    private static class ContactsAdapter extends ArrayAdapter<String> {
    	public ContactsAdapter(Context context, List<String> objects) {
            super(context, 0, objects);
        }
    	@Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;             
            view = LayoutInflater.from(getContext()).inflate(R.layout.contacts_list_item, null);
            TextView textView = (TextView) view.findViewById(R.id.contact_name);
            textView.setText(getItem(position));
            return view;
        }
    }

}


