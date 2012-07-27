package com.androidcamp.teamoverflow.only.local;

import com.androidcamp.teamoverflow.only.local.PlacesService.GetDetailsCallback;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View.OnTouchListener;

public class MarketActivity extends Activity implements GetDetailsCallback {

	private static final String TAG = "ONLY LOCAL";
	private Place mPlace;
	private static final String MAP_URL = "http://gmaps-samples.googlecode.com/svn/trunk/articles-android-webmap/simple-android-map.html";

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

		TextView nameView = (TextView) findViewById(R.id.marketName);
		nameView.setText(place.name);
		
		TextView addressView = (TextView) findViewById(R.id.addressField);
		addressView.setText(place.formatted_address);

		TextView phoneView = (TextView) findViewById(R.id.contactField);
		phoneView.setText(place.formatted_phone_number);

		RatingBar ratings = (RatingBar) findViewById(R.id.ratingBar1);
		Log.d(TAG, "Rating is " + place.rating);
		ratings.setRating(Float.parseFloat(place.rating));
		// To make the stars untouchable
		ratings.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

		setupWebView();

	}

	public void sendToMaps() {
		String address = mPlace.formatted_address;
		Uri location = Uri.parse("geo:0,0?q=address");
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
	}

	/** Sets up the WebView object and loads the URL of the page **/
	private void setupWebView() {

		Log.d(TAG, "lat: " + mPlace.latitude + " lng: " + mPlace.longitude);

		final String centerURL = "javascript:centerAt(" + mPlace.latitude + ","
				+ mPlace.longitude + ")";

		final WebView webView = (WebView) findViewById(R.id.map);
		webView.getSettings().setJavaScriptEnabled(true);
		// Wait for the page to load then send the location information
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				webView.loadUrl(centerURL);
			}
		});
		
		webView.loadUrl(MAP_URL);
	}
}
