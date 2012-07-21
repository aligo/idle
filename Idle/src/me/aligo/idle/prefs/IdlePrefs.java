package me.aligo.idle.prefs;

import me.aligo.idle.R;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

public class IdlePrefs extends SherlockPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addPreferencesFromResource(R.xml.idle_prefs);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    
       switch (item.getItemId()) {        
            case android.R.id.home:            
                finish();
                return true;
            default:            
                return super.onOptionsItemSelected(item);    
       }
    }
}
