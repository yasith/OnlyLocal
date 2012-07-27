package com.androidcamp.teamoverflow.only.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.androidcamp.teamoverflow.only.local.PlacesService.SearchAsyncCallback;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.ListActivity;
import android.content.Intent;

public class SearchActivity extends ListActivity implements SearchAsyncCallback {

	private static final String TAG = "ONLY LOCAL";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "On Create Search Activity");
		super.onCreate(savedInstanceState);
		AppData ad = AppData.getInstance();

		setContentView(R.layout.activity_search);

		if (ad.getFoundLocation()) {
			double lng = ad.getLongitude();
			double lat = ad.getLatitude();

			PlacesService.getInstance().searchAsync("farmers+market", lng, lat,
					this);
			Log.d(TAG, "Location Found in Search");
		} else {
			Log.d(TAG, "No Location Found in Search");
			TextView emptyText = (TextView) findViewById(android.R.id.empty);
			emptyText.setText("No Results Found");
		}
		
        ListView ls = (ListView)findViewById(android.R.id.list);
        ls.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Log.d(TAG, "Position " + position);
					Intent intent = new Intent(getApplicationContext(), MarketActivity.class); //new Intent(this, MarketActivity.class);
					startActivity(intent);
					
					
				}
        });	
	}

	@Override
	public void onSearchCompleted(ArrayList<Place> marketPlaces) {
		List<String> markets = new ArrayList<String>();
		List<String> vicinities = new ArrayList<String>();
		
	    List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();

	    String[] from = {"name", "vicinity"};
	    int[] to = {R.id.market_name, R.id.market_vicinity};

		Log.d(TAG, "Loaded " + markets.size() + " results");

		for (int i = 0; i < marketPlaces.size(); i++) {
			data.add(createRow(marketPlaces.get(i).name, 
							   marketPlaces.get(i).vicinity)
					);
		}
		
		Log.d(TAG, "Done adding list items");
	    SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.search_item, from, to);
		this.setListAdapter(adapter); 
		
		// Adding star rating to the bar
		
		for (int i = 0; i < marketPlaces.size(); i++) {
			View child = this.getListView().getChildAt(i);
			if(child != null){
				RatingBar bar = (RatingBar) child.findViewById(R.id.marketRating);
				bar.setRating(Float.parseFloat(marketPlaces.get(i).rating));
			}
		}
		
		Log.d(TAG, "Done setting list adapter");
	}
	
	private Map<String, ?> createRow(String name, String vicinity) {
		Log.d(TAG, "Adding " + name);
	    Map<String, String> row = new HashMap<String, String>();
	    row.put("name", name);
	    row.put("vicinity", vicinity);
	    return row;
	}
}
