package com.androidcamp.teamoverflow.only.local;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.app.ListActivity;

public class SearchActivity extends ListActivity {

   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_search);
        
        List<String> markets = new ArrayList<String>();
        
        for (int i = 0; i < 200; i++) {
        	markets.add("Market " + (i+1));
		}
        
        this.setListAdapter(new ArrayAdapter(this, R.layout.search_item, R.id.market_name, markets));
    }
}
