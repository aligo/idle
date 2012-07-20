package me.aligo.idle.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionbarsherlock.app.SherlockListFragment;

import me.aligo.idle.adapter.ContactsAdapter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class ContactsFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
    private ContactsAdapter adapter;
    private List<Map<String, String>> contacts;
    private HashMap<String, String> item;
	
    public void onResume() { 
        super.onResume(); 
		getLoaderManager().restartLoader(0, null, this);
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
	
}
