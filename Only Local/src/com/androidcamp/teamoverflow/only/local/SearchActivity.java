package com.androidcamp.teamoverflow.only.local;

import java.util.ArrayList;
import java.util.List;

import com.androidcamp.teamoverflow.only.local.PlacesService.SearchAsyncCallback;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.app.ListActivity;

public class SearchActivity extends ListActivity implements SearchAsyncCallback {

	private static final String TAG = "ONLY LOCAL";
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_search);
        
        double lng = AppData.getInstance().getLongitude();
        double lat = AppData.getInstance().getLatitude();
        PlacesService.getInstance().searchAsync("farmers+market", lng, lat,this);
        
        
        ArrayList<Place> marketPlaces = null;
        
        // Wait until the marketPlace results are loaded, and parsed
        // we have to go this way because AsyncTask onPostExecute
        // is not being called
//        while(marketPlaces == null){
//        	//Log.d(TAG, "Waiting for results to load");
//        	marketPlaces = AppData.getInstance().getResult();
//        }
        
        
        
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
