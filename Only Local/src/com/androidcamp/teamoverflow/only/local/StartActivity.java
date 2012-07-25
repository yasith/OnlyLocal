package com.androidcamp.teamoverflow.only.local;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.androidcamp.teamoverflow.only.local.R;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends Activity {

	private static final String TAG = "ONLY_LOCAL";
	protected String textLoc = " ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        
        //finds and displays Last Known Location
        LocationManager locman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String locprov = LocationManager.NETWORK_PROVIDER;
        Location location = locman.getLastKnownLocation(locprov);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        TextView textView = (TextView) findViewById(R.id.textView2);
        Geocoder geoc = new Geocoder(getBaseContext());
        try {
			List<Address> addresses = geoc.getFromLocation(latitude, longitude, 1);
			Address addy = addresses.get(0);
			textLoc = addy.getLocality();
			
			Log.d(TAG, textLoc);
			
			textView.setText(textLoc);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void openActivity(View view){
    	Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    public void updateLocation(View view) {
    	
    	Log.d(TAG, "Location is being updated");
        
        TextView textView = (TextView) findViewById(R.id.textView2);
       
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
        textLoc = addresses.get(0).getLocality();
        }
       
       
        textView.setText(textLoc);
       
       }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start, menu);
        return true;
    }
}
