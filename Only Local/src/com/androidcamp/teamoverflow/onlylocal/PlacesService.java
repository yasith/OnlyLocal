package com.androidcamp.teamoverflow.onlylocal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

@SuppressLint("ParserError")
public class PlacesService {
    private static final String LOG_TAG = "Only Local";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_DETAILS = "/details";
    private static final String TYPE_SEARCH = "/search";
    private static final String OUT_JSON = "/json";

    // KEY!
    private static final String API_KEY = "AIzaSyDWXJw1lYWFpRqPai3Ws_m3qFSEjXuUQdk";
    
    private static PlacesService sInstance;
    
    private PlacesService() {
    	
    }
    
    public static synchronized PlacesService getInstance() {
    	if(sInstance == null) {
    		sInstance = new PlacesService();
    	}
    	return sInstance;
    }

    public static ArrayList<Place> autocomplete(String input) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(TYPE_AUTOCOMPLETE);
            sb.append(OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<Place>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place();
                place.reference = predsJsonArray.getJSONObject(i).getString("reference");
                place.name = predsJsonArray.getJSONObject(i).getString("description");
                resultList.add(place);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
        }

        return resultList;
    }
    
    public static ArrayList<Place> search(String input, double longitude, double latitude) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(TYPE_SEARCH);
            sb.append(OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&location=" + latitude + "," + longitude);
            sb.append("&radius=50000");
            sb.append("&key=" + API_KEY);
            sb.append("&keyword=" + URLEncoder.encode(input, "utf8"));
            
            Log.d(LOG_TAG, sb.toString());

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<Place>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place();
                place.reference = predsJsonArray.getJSONObject(i).getString("reference");
                place.name = predsJsonArray.getJSONObject(i).getString("name");
                place.vicinity = predsJsonArray.getJSONObject(i).getString("vicinity");
                try{
                	place.rating = predsJsonArray.getJSONObject(i).getString("rating");
                } catch (JSONException e){
                	place.rating = "0";
                }
                resultList.add(place);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
        }

        return resultList;
    }
    
    public static Place details(String reference) {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(TYPE_DETAILS);
            sb.append(OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + API_KEY);
            sb.append("&reference=" + URLEncoder.encode(reference, "utf8"));
        
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
        
            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return null;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        
        Place place = null;
        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString()).getJSONObject("result");
        
            place = new Place();
            place.icon = jsonObj.getString("icon");
            place.name = jsonObj.getString("name");
            place.formatted_address = jsonObj.getString("formatted_address");
            place.formatted_phone_number = jsonObj.getString("formatted_phone_number");
            try{
                	place.rating = jsonObj.getString("rating");
            } catch (JSONException e){
                	place.rating = "0";
            }
            JSONObject location = jsonObj.getJSONObject("geometry").getJSONObject("location");
            place.latitude = location.getString("lat");
            place.longitude = location.getString("lng");
            
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
        }

        return place;
    }
   
    public interface SearchAsyncCallback {
    	void onSearchCompleted( ArrayList<Place> searchResults );
    }
    
    public interface GetDetailsCallback {
    	void onDetailRetrieval(Place place);
    }
    
	public void searchAsync(String place, double longitude, double latitude, SearchAsyncCallback sac ) {
		PlaceSearchRequest psr = new PlaceSearchRequest();
		psr.latitude = latitude;
		psr.longitude = longitude;
		psr.keyword = place;
		
		(new SearchTask(sac)).execute(psr);
	}
	
	public void getDetailsAsync(String reference, GetDetailsCallback gdc) {
		(new DetailsTask(gdc)).execute(reference);
	}
    
	private class SearchTask extends AsyncTask<PlaceSearchRequest, Void, ArrayList<Place> > {
		final SearchAsyncCallback mSAC;
		
		public SearchTask( SearchAsyncCallback sac ) {
			mSAC = sac;
		}
		
		@Override
		protected ArrayList<Place> doInBackground(PlaceSearchRequest... args) {
			
			PlaceSearchRequest psr = args[0];
			Log.d(LOG_TAG, "New Thread Loading Places");
			ArrayList<Place> result = 
					PlacesService.search(psr.keyword, psr.longitude, psr.latitude);
			
			Log.d(LOG_TAG, "Returning places list");
			
			return result;
		}
		
		@Override
		protected void onPostExecute(final ArrayList<Place> result) {
			Log.d(LOG_TAG, "Finished Loading Place");
			mSAC.onSearchCompleted(result);
		}
	}
	
	private class DetailsTask extends AsyncTask<String, Void, Place> {
		final GetDetailsCallback mGDC;
		
		public DetailsTask (GetDetailsCallback gdc) {
			mGDC = gdc;
		}
		
		@Override
		protected Place doInBackground(String... args) {
			Log.d(LOG_TAG, "New Thread getting details about the place");
			
			return PlacesService.details(args[0]);
		}
		
		@Override
		protected void onPostExecute(final Place place) {
			Log.d(LOG_TAG, "Finished getting data");
			mGDC.onDetailRetrieval(place);
		}
	}
	
}

class PlaceSearchRequest {
	double latitude, longitude;
	String keyword;
	
}

class Place{
	public String longitude;
	public String latitude;
	String vicinity;
	String icon;
	String name;
	String formatted_address;
	String formatted_phone_number;
	String reference;
	String rating;
}