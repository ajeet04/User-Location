package com.assignment.leaf.leaf;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocationService extends Service {
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 120000;  // 2 minutes.....
    private static final float LOCATION_DISTANCE = 10f;   // 100 meters
    private static final String TAG="LEAFASSIGN";

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            Log.e("LEAF ASSIGN", "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }
        @Override
        public void onLocationChanged(Location location) {
            mLastLocation.set(location);
            double lat=location.getLatitude();
            double lng= location.getLongitude();
            String address=null;
            if(location!=null)
           address=getAddress(lat,lng);

            NotificationController.showNotification(getApplicationContext(),address);

        }

        @Override
        public void onStatusChanged(String provider, int i, Bundle bundle) {

            Log.e(TAG, "Status Changed : " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "Provider Enabled : " + provider);

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "Provider Disabled : " + provider);

        }
    }

    private String getAddress(double lat, double lng) {
        String add=null;

        Geocoder geocoder=new Geocoder(getApplicationContext(),Locale.getDefault());
        try{

            List<Address> addresses=geocoder.getFromLocation(lat,lng,1);
            if(addresses.size()>0){
                Address obj=addresses.get(0);
                add=obj.getAddressLine(0);
                return add;
            }
            else{
                return "No address found";
            }


        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }

    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {

            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[1]);

        } catch (java.lang.SecurityException ex) {

            Log.i(TAG, "Fail to request location update, ignore", ex);

        } catch (IllegalArgumentException ex) {

            Log.d(TAG, "Network provider does not exist, " + ex.getMessage());
        }
        try {

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[0]);

        } catch (java.lang.SecurityException ex) {

            Log.i(TAG, "Fail to request location update, ignore", ex);

        } catch (IllegalArgumentException ex) {

            Log.d(TAG, "GPS provider does not exist " + ex.getMessage());
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initializeLocationManager() {
        Log.e(TAG, "Initialize Location Manager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
        }
    }
}
