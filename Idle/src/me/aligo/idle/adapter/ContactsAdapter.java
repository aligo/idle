package me.aligo.idle.adapter;

import java.util.List;
import java.util.Map;

import me.aligo.idle.R;
import me.aligo.idle.fragment.ContactsFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<Map<String, String>> {

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
