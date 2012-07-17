package com.aligo.idle;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public final class Main extends TabActivity
{
	private TabHost mTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTabHost = getTabHost();
    
        Intent intent1 = new Intent(this,com.aligo.idle.contacts.Activity.class);
        createTab("Idle",intent1);
    
        mTabHost.setCurrentTab(1);
    }
    private void createTab(String text ,Intent intent){
    	mTabHost.addTab(mTabHost.newTabSpec(text).setIndicator(createTabView(text)).setContent(intent));
    }
    private View createTabView(String text) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        TextView tt = (TextView) view.findViewById(R.id.tabsText);
        tt.setText(text);
        return view;
    }
}
