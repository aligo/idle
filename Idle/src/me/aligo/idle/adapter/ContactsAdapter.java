package me.aligo.idle.adapter;

import java.util.ArrayList;

import me.aligo.idle.R;
import me.aligo.idle.fragment.ContactsFragment;
import me.aligo.idle.model.BaseModel;
import me.aligo.idle.model.Contact;
import me.aligo.idle.model.ListSection;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<BaseModel> {
  
  private LayoutInflater vi;

    public ContactsAdapter(ContactsFragment contactsFragment, Context context, ArrayList<BaseModel> items) {
        super(context, 0, items);
        vi = LayoutInflater.from(getContext());
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        BaseModel item = getItem(position);
        if (item != null) {
            if (item.isListSection()) {
                ListSection lsi = (ListSection) item;
                v = vi.inflate(R.layout.contact_item_header, null);
              
                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setLongClickable(false);
                
                TextView textView = (TextView) v.findViewById(R.id.title);
                textView.setText(lsi.getTitle());
            } else {
                Contact ci = (Contact) item;
                v = vi.inflate(R.layout.contact_item, null);
                TextView textView = (TextView) v.findViewById(R.id.display_name);
                textView.setText(ci.getDisplayName());
            }
        }
        return v;
    }
}
