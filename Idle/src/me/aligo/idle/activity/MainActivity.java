package me.aligo.idle.activity;

import me.aligo.idle.fragment.ContactsFragment;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
	        ContactsFragment contactsFragment = new ContactsFragment();
	    	getSupportFragmentManager().beginTransaction().add(android.R.id.content, contactsFragment).commit();
        }
    }
   
}