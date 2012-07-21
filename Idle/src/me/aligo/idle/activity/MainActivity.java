package me.aligo.idle.activity;

import me.aligo.idle.R;
import me.aligo.idle.fragment.ContactsFragment;
import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.prefs:
                Intent intent = new Intent(this, me.aligo.idle.prefs.IdlePrefs.class);
                startActivityForResult(intent, 0);
                return true;
            default:            
                return super.onOptionsItemSelected(item);    
       }
    }
   
}