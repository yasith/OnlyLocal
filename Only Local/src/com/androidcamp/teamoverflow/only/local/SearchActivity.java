package com.androidcamp.teamoverflow.only.local;

import java.util.ArrayList;
import java.util.List;

import com.androidcamp.teamoverflow.only.local.PlacesService.SearchAsyncCallback;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListActivity;

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
				}
        });	
	}

	@Override
	public void onSearchCompleted(ArrayList<Place> marketPlaces) {
		List<String> markets = new ArrayList<String>();
		List<String> vicinities = new ArrayList<String>();

		Log.d(TAG, "Loaded " + markets.size() + " results");

		for (int i = 0; i < marketPlaces.size(); i++) {
			markets.add(marketPlaces.get(i).name);
//			vicinities.add(marketPlaces.get(i).vicinity);
		}
		this.setListAdapter(new ArrayAdapter(this, R.layout.search_item,
				R.id.market_name, markets));
	}

}
