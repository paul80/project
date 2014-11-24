package ca.ualberta.cs.queueunderflow;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * The Class LocationHandler.
 */
public class LocationHandler implements LocationListener{

	
	/** The location manager. */
	LocationManager locManager;
	
	/** The ctx. */
	Context ctx;
	
	//Static variables.
	/** The longitude. */
	public static double longitude;
	
	/** The latitude. */
	public static double latitude;
	
	/** The listening gps. */
	public static boolean listeningGPS = false;
	
	/** The min gps update time. */
	final long minGPSUpdateTime = 0; //This is how often GPS will check for new position in milliseconds.
										  //Lower values drains battery apparently
	
	/**
  										 * Instantiates a new location handler.
  										 *
  										 * @param context the context
  										 */
  										public LocationHandler(Context context)
	{
		ctx = context;
		locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
	}
	
	/**
	 * Checks if GPS is enabled. Returns true if it is, false if it isn't.
	 * @return Boolean that states if GPS is enabled.
	 */
	public boolean isGPSEnabled()
	{
		if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			//GPS is not enabled.
			return false;
		}
		return true;
	}
	
	/**
	 * Return a string indicating the location from GPS coordinates.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @return Returns in format "City|Country". Returns null if unable to find one.
	 */
	public String getLocationFromCoordinates(double latitude, double longitude)
	{
		//This returns a string formated like "CITY, COUNTRY"
		//eg. "Edmonton,Canada"
		
		//This REQUIRES an Internet connection to run.
		
		
		//Lots of this is taken from http://developer.android.com/training/location/display-address.html
		Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
		List<Address> addresses = null;
		
		try {
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
		} catch (IOException e1) {
			// IOException. No idea when this will run.
			return null;
		} catch (IllegalArgumentException e2) {
			//Illegal arguments! Something wrong with the coordinates passed.
			//Most likely longitude and latitude is unset with no values available.
			//For now, let's just say the user is at "Unknown"
			return null;
		}
		
		if (addresses != null && addresses.size() > 0) {
			
			//It returned an address.
			
			String addressString;
			
			Address address = addresses.get(0);
			addressString = String.format("%s|%s", address.getLocality(), address.getCountryName());
			return addressString;
			
		} else {
			
			//If getFromLocation returns no addresses at all.......
			//This happens if the user isn't in a country
			//example: adding questions while in the middle of the Atlantic ocean.
			
			//In this situation what are we supposed to do?
			//For now, return unknown. (maybe return coordinates later)?
			
			return null;
			
		}
		
	}
	
	/**
	 * Tell Location to stop listening for GPS update events.
	 */
	public void GPSUnlisten()
	{
		locManager.removeUpdates(this);
	}
	
	/**
	 * Tell Location to listen for GPS update events. New data is stored in latitude/longitude.
	 *
	 * @param location the location
	 */
	public void onLocationChanged(Location location)
	{
		Double latitude = location.getLatitude();
		Double longitude = location.getLongitude();
		
		LocationHandler.latitude = latitude;
		LocationHandler.longitude = longitude;
		LocationHandler.listeningGPS = false;
		GPSUnlisten();
	}
	
	/**
	 * Listen for GPS updates (only updates ONCE).
	 */
	public void GetGPSLocation()
	{
		//ONLY USE THIS IN ANOTHER THREAD
		LocationHandler.listeningGPS = true;
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minGPSUpdateTime, 0, this);
	}
	
	//Useless functions we don't need.
	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	public void onProviderDisabled(String arg) {}
	
	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	public void onProviderEnabled(String arg) {}
	
	/* (non-Javadoc)
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	public void onStatusChanged(String arg, int arg2, Bundle arg3) {}
	
}
