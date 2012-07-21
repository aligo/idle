package me.aligo.idle.fragment;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockListFragment;

import me.aligo.idle.adapter.ContactsAdapter;
import me.aligo.idle.model.BaseModel;
import me.aligo.idle.model.Contact;
import me.aligo.idle.model.ListSection;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class ContactsFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    
    private ContactsAdapter adapter;
    private ArrayList<BaseModel> items;
    
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
        items = new ArrayList<BaseModel>();
        items.add(new ListSection("我"));
        items.add(new Contact("我的名字"));
        if (cursor.getCount() > 0) {
            items.add(new ListSection("好友 (" + cursor.getCount() + ")"));
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                items.add(new Contact(name));
            }
        }
        adapter = new ContactsAdapter(this, getActivity(), items);
        setListAdapter(adapter);
    }
 
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
    
}
