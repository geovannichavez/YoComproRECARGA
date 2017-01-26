package com.globalpaysolutions.yocomprorecarga.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.location.GoogleLocationApiManager;
import com.globalpaysolutions.yocomprorecarga.location.LocationCallback;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IHomePresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.AcceptTerms;
import com.globalpaysolutions.yocomprorecarga.ui.activities.TokenInput;
import com.globalpaysolutions.yocomprorecarga.ui.activities.ValidatePhone;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.HomeView;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public class HomePresenterImpl implements IHomePresenter, LocationCallback
{
    private static final String TAG = HomePresenterImpl.class.getSimpleName();

    private HomeView mView;
    private Context mContext;
    private UserData mUserData;
    private Activity mActivity;

    private GoogleLocationApiManager mGoogleLocationApiManager;

    public HomePresenterImpl(HomeView pView, AppCompatActivity pActivity, Context pContext)
    {
        mView = pView;
        mContext = pContext;
        mUserData = new UserData(mContext);
        mActivity = pActivity;

        this.mGoogleLocationApiManager = new GoogleLocationApiManager(pActivity, mContext);
        this.mGoogleLocationApiManager.setLocationCallback(this);

    }

    @Override
    public void setInitialViewsState()
    {
        this.mView.renderMap();
    }

    @Override
    public void checkUserDataComplited()
    {
        if(!mUserData.UserAcceptedTerms())
        {
            Intent acceptTerms = new Intent(mActivity, AcceptTerms.class);
            mContext.startActivity(acceptTerms);
        }
        else if (!mUserData.UserSelectedCountry())
        {
            Intent selectCountry = new Intent(mActivity, ValidatePhone.class);
            mContext.startActivity(selectCountry);
        }
        else if(!mUserData.UserVerifiedPhone())
        {
            Intent inputToken = new Intent(mActivity, TokenInput.class);
            mContext.startActivity(inputToken);
        }
    }

   @Override
   public void chekcLocationServiceEnabled()
   {
       LocationManager locationManager;
       boolean gpsEnabled = false;
       boolean networkEnabled = false;

       try
       {
           locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
           gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
           networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }

       if(!gpsEnabled && !networkEnabled)
       {
            mView.displayActivateLocationDialog();
       }
   }

    @Override
    public void checkPermissions()
    {
        mView.checkPermissions();
    }

    @Override
    public void connnectToLocationService()
    {
        Log.d(TAG, "connectToLocationService: hit");
        mGoogleLocationApiManager.connect();
    }

    @Override
    public void disconnectFromLocationService()
    {
        Log.d(TAG, "disconnectFromLocationService: hit");
        mGoogleLocationApiManager.disconnect();
    }

    @Override
    public void onMapReady()
    {
        connnectToLocationService();
    }

    /*
    *
    *   LocationCallback
    *
    */
    @Override
    public void onLocationChanged(Location location)
    {
        mView.updateUserLocationOnMap(location);
    }

    @Override
    public void onLocationApiManagerConnected(Location location)
    {
        mView.setInitialUserLocation(location);
    }
}
