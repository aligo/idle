package me.aligo.idle.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionbarsherlock.app.SherlockListFragment;

import me.aligo.idle.R;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
    private ContactsAdapter adapter;
    private List<Map<String, String>> contacts;
    private HashMap<String, String> item;
	
    public void onResume() { 
        super.onResume(); 
		getLoaderManager().initLoader(0, null, this);
    }
	
	@Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		CursorLoader cursorLoader = new CursorLoader(getActivity(), ContactsContract.Contacts.CONTENT_URI, projection, null, null, sortOrder);
	    return cursorLoader;
    }
 
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    	contacts = new ArrayList<Map<String, String>>();
    	addMyself();
        if (cursor.getCount() > 0) {
        	item = new HashMap<String, String>();
            item.put("name", "Contacts (" + cursor.getCount() + ")");
            item.put("type", "header");
            contacts.add(item);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                item = new HashMap<String, String>();
                item.put("name", name);
                item.put("type", "contact");
                contacts.add(item);
            }
        }
        adapter = new ContactsAdapter(this, getActivity(), contacts);
        setListAdapter(adapter);
    }
 
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TBD
    }
    
    private void addMyself() {
    	item = new HashMap<String, String>();
    	item.put("name", "Myself");
    	item.put("type", "header");
        contacts.add(item);
        item = new HashMap<String, String>();
        item.put("name", "My name");
        item.put("type", "self");
        contacts.add(item);
    }
	
    private static class ContactsAdapter extends ArrayAdapter<Map<String, String>> {

		public ContactsAdapter(ContactsFragment contactsFragment, Context context, List<Map<String, String>> contacts) {
			super(context, 0, contacts);
		}
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Map<String, String> map = getItem(position);
            if ( map.get("type") == "header") {
            	view = LayoutInflater.from(getContext()).inflate(R.layout.contact_item_header, null);
                TextView textView = (TextView) view.findViewById(R.id.name);
                textView.setText(getItem(position).get("name"));
            } else {
            	view = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, null);
                TextView textView = (TextView) view.findViewById(R.id.name);
                textView.setText(getItem(position).get("name"));
            }
            return view;
        }
    }
	
}
