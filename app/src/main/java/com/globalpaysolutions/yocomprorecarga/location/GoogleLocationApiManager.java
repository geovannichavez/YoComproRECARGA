package com.globalpaysolutions.yocomprorecarga.location;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by Josué Chávez on 23/01/2017.
 */

public class GoogleLocationApiManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>, LocationListener
{
    private static final String TAG = GoogleLocationApiManager.class.getSimpleName();

    private static final int LOCATION_REQUEST_INTERVAL = 6000;
    private static final int LOCATION_REQUEST_FASTEST_INTERVAL = 3000;
    private static final int LOCATION_REQUEST_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastKnownLocation;

    private LocationRequest mLocationRequest;
    private LocationSettingsRequest.Builder mLocationSettingsRequestBuilder;
    private PendingResult<LocationSettingsResult> pendingResult;

    private LocationCallback locationCallback;


    public GoogleLocationApiManager(AppCompatActivity pActivity, Context pContext, float displacementMetters)
    {
        this.mContext = pContext;
        this.mGoogleApiClient = new GoogleApiClient.Builder(pActivity)
                                            .enableAutoManage(pActivity, this)
                                            .addApi(LocationServices.API)
                                            .addConnectionCallbacks(this)
                                            .addOnConnectionFailedListener(this)
                                            .build();

        this.mLocationRequest = new LocationRequest();
        this.mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        this.mLocationRequest.setFastestInterval(LOCATION_REQUEST_FASTEST_INTERVAL);
        this.mLocationRequest.setPriority(LOCATION_REQUEST_PRIORITY);
        this.mLocationRequest.setSmallestDisplacement(displacementMetters);

        this.mLocationSettingsRequestBuilder = new LocationSettingsRequest.Builder().addLocationRequest(this.mLocationRequest);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.d(TAG, "onConnected: permissions not granted");
            return;
        }

        pendingResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, mLocationSettingsRequestBuilder.build());

        mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        if (mLastKnownLocation != null)
        {
            Log.i(TAG, "onConnected: lat " + mLastKnownLocation.getLatitude());
            Log.i(TAG, "onConnected: long " + mLastKnownLocation.getLongitude());
        }

        locationCallback.onLocationApiManagerConnected(mLastKnownLocation);

    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.d(TAG, "onConnectionSuspended: hit");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.d(TAG, "onConnectionFailed: hit");
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult)
    {
        Log.d(TAG, "onResult: hit");

        final Status status = locationSettingsResult.getStatus();

        switch (status.getStatusCode())
        {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "onResult: status - SUCCESS");
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "onResult: status - RESOLUTION_REQUIRED");
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "onResult: status - SETTINGS_CHANGE_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        Log.d(TAG, "onLocationChanged: hit");
        mLastKnownLocation = location;

        if (locationCallback != null)
        {
            locationCallback.onLocationChanged(location);
        }
    }


    /**
     * Connect to {@link LocationServices}
     */
    public void connect()
    {
        Log.d(TAG, "connect: hit");
        mGoogleApiClient.connect();
    }

    /**
     * Disconnect from {@link LocationServices}
     */
    public void disconnect()
    {
        Log.d(TAG, "disconnect: hit");
        mGoogleApiClient.disconnect();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public GoogleApiClient getGoogleApiClient()
    {
        return mGoogleApiClient;
    }

    public boolean isConnectionEstablished()
    {
        return mGoogleApiClient.isConnected();
    }

    public void setLocationCallback(LocationCallback locationCallback)
    {
        this.locationCallback = locationCallback;
    }

    /*public void changeSmallestDisplacement(float displacementMeters)
    {
        this.mLocationRequest.setSmallestDisplacement(displacementMeters);
    }*/

}
