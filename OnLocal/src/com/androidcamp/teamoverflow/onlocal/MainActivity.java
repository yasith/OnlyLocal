package com.androidcamp.teamoverflow.onlocal;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.location.Address;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	
	// Tag for logging
	private static final String TAG = "ON_LOCAL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Called when the user taps the Update Location Button
     */
    public void updateLocation(View view) {
    
    	Log.d(TAG, "Updating Location");
    	TextView textView = (TextView) findViewById(R.id.location_label);
    
    	LocationManager locationManager = (LocationManager) 
    			this.getSystemService(Context.LOCATION_SERVICE);

    	
    	// Or use GPS_PROVIDER
    	String locationProvider = LocationManager.NETWORK_PROVIDER;

    	Location lastKnownLocation = 
    			locationManager.getLastKnownLocation(locationProvider);
    	
    	double latitude = lastKnownLocation.getLatitude();
    	double longitude = lastKnownLocation.getLongitude();
    	
    	Geocoder gcd = new Geocoder(this, Locale.getDefault());
    	List<Address> addresses = null;
    	String position = "";
    	
		try {
			addresses = gcd.getFromLocation(latitude, longitude, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	if (addresses.size() > 0) {
    	    position = addresses.get(0).getLocality();
    	}
    	
    	
    	textView.setText(position);
    	
    }
    
    
    /**
     * Called when user taps the clear Location Button
     */
    
    public void clearLocation(View view) {
    	Log.d(TAG, "Clearing Location");
    	TextView textView = (TextView) findViewById(R.id.location_label);
    	
    	textView.setText("");
    }
    
}
