package com.androidcamp.teamoverflow.only.local;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MarketActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_market);
	}
	
	public void sendToMaps(String address){
		Uri location = Uri.parse("geo:0,0?q=address");
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);	
	}
}
