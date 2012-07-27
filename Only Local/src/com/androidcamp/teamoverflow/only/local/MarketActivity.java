package com.androidcamp.teamoverflow.only.local;

import com.androidcamp.teamoverflow.only.local.PlacesService.GetDetailsCallback;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MarketActivity extends Activity implements GetDetailsCallback{
	
	private static final String TAG = "ONLY LOCAL";
	private Place mPlace;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_market);
		
		String ref = AppData.getInstance().getSelectedPlaceReference();
		PlacesService.getInstance().getDetailsAsync(ref, this);
	}

	@Override
	public void onDetailRetrieval(Place place) {
		mPlace = place;
		Log.d(TAG, "Got Details " + place.name);
		
		TextView addressView = (TextView) findViewById(R.id.addressField); 
		addressView.setText(place.formatted_address);
	}
	
	public void sendToMaps(){
		String address = mPlace.formatted_address;
		Uri location = Uri.parse("geo:0,0?q=address");
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);	
	}
}
