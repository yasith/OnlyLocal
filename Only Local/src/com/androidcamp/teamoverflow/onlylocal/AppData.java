package com.androidcamp.teamoverflow.onlylocal;

public class AppData {

	// The singleton instance
	private static AppData sInstance = null;
	
	// Data
	private String mLocationName = null;
	private double mLongitude;
	private double mLatitude;
	private boolean mFoundLocation = false;
	
	private String mSelectedPlaceReference = null;
	
	private double mLocalLat;
	private double mLocalLng;
	
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

	/**
	 * @return the mFoundLocation
	 */
	public boolean getFoundLocation() {
		return mFoundLocation;
	}

	/**
	 * @param mFoundLocation the mFoundLocation to set
	 */
	public void setFoundLocation(boolean mFoundLocation) {
		this.mFoundLocation = mFoundLocation;
	}

	/**
	 * @return the mSelectedPlaceReference
	 */
	public String getSelectedPlaceReference() {
		return mSelectedPlaceReference;
	}

	/**
	 * @param mSelectedPlaceReference the mSelectedPlaceReference to set
	 */
	public void setSelectedPlaceReference(String mSelectedPlaceReference) {
		this.mSelectedPlaceReference = mSelectedPlaceReference;
	}

	/**
	 * @return the mLocalLat
	 */
	public double getLocalLat() {
		return mLocalLat;
	}

	/**
	 * @param mLocalLat the mLocalLat to set
	 */
	public void setLocalLat(double mLocalLat) {
		this.mLocalLat = mLocalLat;
	}

	/**
	 * @return the mLocalLng
	 */
	public double getLocalLng() {
		return mLocalLng;
	}

	/**
	 * @param mLocalLng the mLocalLng to set
	 */
	public void setLocalLng(double mLocalLng) {
		this.mLocalLng = mLocalLng;
	}
}
