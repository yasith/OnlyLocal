package com.androidcamp.teamoverflow.onlylocal;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class StartActivity extends Activity {

	private static final String TAG = "ONLY_LOCAL";
	protected String textLoc = " ";
	
	private String mLocalName = "";
	private double mLocalLat;
	private double mLocalLng;
	private boolean mLocalFound = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i(TAG, "On Create");
		
		setContentView(R.layout.activity_start);
		setLocation();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Log.i(TAG, "On Resume");
		
		EditText editTextView = (EditText) findViewById(R.id.locationText);
		editTextView.setText("");
		editTextView.setHint(mLocalName);
	}

	public void openActivity(View view) {
		
		EditText editTextView = (EditText) findViewById(R.id.locationText);
		textLoc = editTextView.getText().toString();
		
		if(textLoc.length() == 0){
			Log.d(TAG, "Text is empty");
			if(mLocalFound){
				Log.d(TAG, "Local location found");
				AppData ad = AppData.getInstance();
				ad.setLatitude(mLocalLat);
				ad.setLongitude(mLocalLng);
				
				ad.setFoundLocation(true);
			}
		}else{
		
			Log.d(TAG, "Text is not empty:" + textLoc);
			Geocoder placeGeo = new Geocoder(this, Locale.getDefault());
			try {
				List<Address> addyList= placeGeo.getFromLocationName(textLoc, 1);
				
				AppData ad = AppData.getInstance();
				if(addyList.size() > 0){
					Address addy = addyList.get(0);
					double longitude = addy.getLongitude();
					double latitude = addy.getLatitude();
					ad.setLocationName(textLoc);
					ad.setLatitude(latitude);
					ad.setLongitude(longitude);
					ad.setFoundLocation(true);
					Log.d(TAG, "Setting the location to true");
				}else {
					ad.setFoundLocation(false);
					Log.d(TAG, "Setting the location to false");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_start, menu);
		return true;
	}
	
	/*
	 * Sets the current location in the TextField
	 */
	private void setLocation() {
		
		EditText locationText = (EditText) findViewById(R.id.locationText);

		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// We just need the rough location, don't need an accurate location
		String locationProvider = LocationManager.NETWORK_PROVIDER;

		// Finding the location takes time, last known location would be
		// sufficient for our needs
		Location lastKnownLocation = 
				locationManager.getLastKnownLocation(locationProvider);

		double latitude = lastKnownLocation.getLatitude();
		double longitude = lastKnownLocation.getLongitude();

		Geocoder gcd = new Geocoder(this, Locale.getDefault());
		
		List<Address> addresses = null;

		try {
			addresses = gcd.getFromLocation(latitude, longitude, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Store location information in the singleton, as we need
		// the information in the future
		AppData ad = AppData.getInstance();
		
		// TODO: Would crash if not connected to a network. Temp. fix in place
		// TODO: Fix the insides of this if block
		if (addresses != null && addresses.size() > 0) {
			textLoc = addresses.get(0).getLocality();
			String statename = addresses.get(0).getAdminArea();
			String stateabb = stateAbbreviation(statename);
			textLoc = textLoc + ", " + stateabb;
			
			ad.setLocationName(textLoc);
			ad.setLatitude(latitude);
			ad.setLongitude(longitude);
			ad.setFoundLocation(true);
			mLocalFound = true;
			mLocalLat = latitude;
			mLocalLng = longitude;
			mLocalName = textLoc;
			ad.setLocalLat(latitude);
			ad.setLocalLng(longitude);
			Log.d(TAG, "Setting Location to true");
		}else {
			ad.setFoundLocation(false);
			Log.d(TAG, "Setting Location to false");
		}

		locationText.setHint(this.getString(R.string.textHint, textLoc));
	}
	
	/**
	 * Returns the abbreviation for given U.S.A State.
	 * 
	 * TODO: Make it work for states other than California
	 */
	private String stateAbbreviation(String state) {
		if (state.toLowerCase().equals("california")) {
			return "CA";
		}
		
		return state;
	}
}
