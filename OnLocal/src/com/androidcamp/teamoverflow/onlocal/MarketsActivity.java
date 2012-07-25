package com.androidcamp.teamoverflow.onlocal;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MarketsActivity extends ListActivity {

	// Tag for logging
	private static final String TAG = "ON_LOCAL";

	static final String[] MARKETS = 
			new String[] {"Market 1", "Market 2", "Market 3"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}
}
