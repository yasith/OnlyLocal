package com.androidcamp.teamoverflow.only.local;

public class AppData {

	// The singleton instance
	private static AppData sInstance = null;
	
	// Data
	private String mLocationName = null;
	private double mLongitude;
	private double mLatitude;
	
	
	
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
	
}
