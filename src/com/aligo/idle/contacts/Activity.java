package com.aligo.idle.contacts;

import java.util.ArrayList;

import com.aligo.idle.R;
import com.aligo.idle.widget.PinnedHeaderListView;
import com.aligo.idle.widget.PinnedHeaderListView.PinnedHeaderAdapter;
import com.aligo.idle.widget.StringArrayAlphabetIndexer;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;


public class Activity extends ListActivity {

    private NamesAdapter mAdapter;
   
    private int mPinnedHeaderBackgroundColor;
    private int mPinnedHeaderTextColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        String[] names = getContacts();
        mAdapter = new NamesAdapter(this, R.layout.contacts_list_item, android.R.id.text1, names);
        setListAdapter(mAdapter);
       
        setupListView();
    }
   
    private String[] getContacts() {
    	ArrayList<String> contacts_name = new ArrayList<String>();
    	Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
//        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor cur =  managedQuery(uri, projection, null, null, null);
    	if (cur.getCount() > 0) {
    		while (cur.moveToNext()) {
    			String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    			contacts_name.add(name);
    		}
    	}
    	String[] result = new String[ contacts_name.size() ];
    	contacts_name.toArray( result );
    	return result;
    }
    
    private void setupListView() {
    	PinnedHeaderListView listView = (PinnedHeaderListView) findViewById(android.R.id.list);
        listView.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.contacts_list_item_header, listView, false));
        listView.setOnScrollListener(mAdapter);
        listView.setDividerHeight(0);
    }
   
    private final class NamesAdapter extends ArrayAdapter<String> implements SectionIndexer, OnScrollListener, PinnedHeaderAdapter {
           
        private SectionIndexer mIndexer;

        public NamesAdapter(Context context, int resourceId, int textViewResourceId, String[] objects) {
                super(context, resourceId, textViewResourceId, objects);
               
                this.mIndexer = new StringArrayAlphabetIndexer(objects, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
               
                bindSectionHeader(v, position);
               
                return v;
        }

        private void bindSectionHeader(View itemView, int position) {
            final TextView headerView = (TextView) itemView.findViewById(R.id.header_text);
            final View dividerView = itemView.findViewById(R.id.list_divider);
           
            final int section = getSectionForPosition(position);
            if (getPositionForSection(section) == position) {
                String title = (String) mIndexer.getSections()[section];
                headerView.setText(title);
                headerView.setVisibility(View.VISIBLE);
                dividerView.setVisibility(View.GONE);
            } else {
                headerView.setVisibility(View.GONE);
                dividerView.setVisibility(View.VISIBLE);
            }
   
            // move the divider for the last item in a section
            if (getPositionForSection(section + 1) - 1 == position) {
                dividerView.setVisibility(View.GONE);
            } else {
                dividerView.setVisibility(View.VISIBLE);
            }
        }
   
        public int getPositionForSection(int sectionIndex) {
            if (mIndexer == null) {
                return -1;
            }

            return mIndexer.getPositionForSection(sectionIndex);
        }
           
        public int getSectionForPosition(int position) {
            if (mIndexer == null) {
                return -1;
            }

            return mIndexer.getSectionForPosition(position);
        }
           
        public Object[] getSections() {
            if (mIndexer == null) {
                return new String[] { " " };
            } else {
                return mIndexer.getSections();
            }
        }
     
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (view instanceof PinnedHeaderListView) {
                ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
            }          
        }

        public void onScrollStateChanged(AbsListView arg0, int arg1) {
        }

        public int getPinnedHeaderState(int position) {
            if (mIndexer == null || getCount() == 0) {
                return PINNED_HEADER_GONE;
            }

            if (position < 0) {
                return PINNED_HEADER_GONE;
            }

            // The header should get pushed up if the top item shown
            // is the last item in a section for a particular letter.
            int section = getSectionForPosition(position);
            int nextSectionPosition = getPositionForSection(section + 1);
           
            if (nextSectionPosition != -1 && position == nextSectionPosition - 1) {
                return PINNED_HEADER_PUSHED_UP;
            }

            return PINNED_HEADER_VISIBLE;
        }

        public void configurePinnedHeader(View v, int position, int alpha) {
            TextView header = (TextView) v;
           
            final int section = getSectionForPosition(position);
            final String title = (String) getSections()[section];
           
            header.setText(title);
            if (alpha == 255) {
                header.setBackgroundColor(mPinnedHeaderBackgroundColor);
                header.setTextColor(mPinnedHeaderTextColor);
            } else {
                header.setBackgroundColor(Color.argb(alpha,
                                Color.red(mPinnedHeaderBackgroundColor),
                                Color.green(mPinnedHeaderBackgroundColor),
                                Color.blue(mPinnedHeaderBackgroundColor)));
                header.setTextColor(Color.argb(alpha,
                                Color.red(mPinnedHeaderTextColor),
                                Color.green(mPinnedHeaderTextColor),
                                Color.blue(mPinnedHeaderTextColor)));
            }
        }
           
    }

}


