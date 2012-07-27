package com.androidcamp.teamoverflow.only.local;

import com.androidcamp.teamoverflow.only.local.PlacesService.GetDetailsCallback;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View.OnTouchListener;

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
		
		TextView phoneView = (TextView) findViewById(R.id.contactField);
		phoneView.setText(place.formatted_phone_number);
		
		RatingBar ratings = (RatingBar) findViewById(R.id.ratingBar1);
		Log.d(TAG, "Rating is " + place.rating);
		ratings.setRating(Float.parseFloat(place.rating));
		ratings.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
	    });	
		
	}
	
	public void sendToMaps(){
		String address = mPlace.formatted_address;
		Uri location = Uri.parse("geo:0,0?q=address");
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);	
	}
}
