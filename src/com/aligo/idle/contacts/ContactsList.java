package com.aligo.idle.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Map<String, String>> contacts;
    private HashMap<String, String> itemmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        adapter = new ContactsAdapter(this, getContacts());
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
   
    private List<Map<String, String>> getContacts() {
        contacts = new ArrayList<Map<String, String>>();
        addMyself();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor cur =  managedQuery(uri, projection, null, null, sortOrder);
        if (cur.getCount() > 0) {
        	itemmap = new HashMap<String, String>();
            itemmap.put("name", cur.getCount() + " contacts");
            itemmap.put("type", "header");
            contacts.add(itemmap);
            while (cur.moveToNext()) {
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                itemmap = new HashMap<String, String>();
                itemmap.put("name", name);
                itemmap.put("type", "contact");
                contacts.add(itemmap);
            }
        }
        return contacts;
    }
    
    private void addMyself() {
    	itemmap = new HashMap<String, String>();
    	itemmap.put("name", "Myself");
    	itemmap.put("type", "header");
        contacts.add(itemmap);
        itemmap = new HashMap<String, String>();
        itemmap.put("name", "My name");
        itemmap.put("type", "self");
        contacts.add(itemmap);
    }
    
    private static class ContactsAdapter extends ArrayAdapter<Map<String, String>> {
        public ContactsAdapter(Context context, List<Map<String, String>> objects) {
            super(context, 0, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Map<String, String> map = getItem(position);
            if ( map.get("type") == "header") {
            	view = LayoutInflater.from(getContext()).inflate(R.layout.contacts_list_item_header, null);
                TextView textView = (TextView) view.findViewById(R.id.name);
                textView.setText(getItem(position).get("name"));
            } else {
            	view = LayoutInflater.from(getContext()).inflate(R.layout.contacts_list_item, null);
                TextView textView = (TextView) view.findViewById(R.id.name);
                textView.setText(getItem(position).get("name"));
            }
            return view;
        }
    }

}


