package com.androidcamp.teamoverflow.only.local;

import java.util.ArrayList;
import java.util.List;

import com.androidcamp.teamoverflow.only.local.PlacesService.SearchAsyncCallback;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.app.ListActivity;

public class SearchActivity extends ListActivity implements SearchAsyncCallback {

	private static final String TAG = "ONLY LOCAL";
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppData ad = AppData.getInstance();
        
        setContentView(R.layout.activity_search);
        
	    if(ad.getFoundLocation()) {
	        double lng = ad.getLongitude();
	        double lat = ad.getLatitude();
	        
	        PlacesService.getInstance().searchAsync("farmers+market", lng, lat,this);
        }else {
        	TextView emptyText = (TextView) findViewById(android.R.id.empty);
        	emptyText.setText("No Results Found");
        }
  }

	@Override
	public void onSearchCompleted(ArrayList<Place> marketPlaces) {
        List<String> markets = new ArrayList<String>();
        
        Log.d(TAG, "Loaded " + markets.size() + " results");
        
        for (int i = 0; i < marketPlaces.size(); i++) {
        	markets.add(marketPlaces.get(i).name);
		}
		this.setListAdapter(new ArrayAdapter(this, R.layout.search_item, R.id.market_name, markets));
		
	}
	
	
}
