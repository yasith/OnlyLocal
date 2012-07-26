package com.androidcamp.teamoverflow.only.local;

import java.util.ArrayList;

public class AppData {

	// The singleton instance
	private static AppData sInstance = null;
	
	// Data
	private String mLocationName = null;
	private double mLongitude;
	private double mLatitude;

	private ArrayList<Place> mResult;
	
	/**
	 * Returns the AppData instance
	 */
	public static synchronized AppData getInstance() {
		if(sInstance == null){
			sInstance = new AppData();
		}
		return sInstance;
	}
	
	private AppData(){
		
	}

	/**
	 * @return the mLocationName
	 */
	public String getLocationName() {
		return mLocationName;
	}

	/**
	 * @param mLocationName the mLocationName to set
	 */
	public void setLocationName(String mLocationName) {
		this.mLocationName = mLocationName;
	}

	/**
	 * @return the mLongitude
	 */
	public double getLongitude() {
		return mLongitude;
	}

	/**
	 * @param mLongitude the mLongitude to set
	 */
	public void setLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}

	/**
	 * @return the mLatitude
	 */
	public double getLatitude() {
		return mLatitude;
	}

	/**
	 * @param mLatitude the mLatitude to set
	 */
	public void setLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}

	public void setResult(ArrayList<Place> result) {
		this.mResult = result;
	}
	
	public ArrayList<Place> getResult() {
		return mResult;
	}
}
