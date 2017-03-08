package com.globalpaysolutions.yocomprorecarga.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.interactors.HomeInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.HomeListener;
import com.globalpaysolutions.yocomprorecarga.location.GoogleLocationApiManager;
import com.globalpaysolutions.yocomprorecarga.location.LocationCallback;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.SalePointData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.VendorPointData;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IHomePresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.AcceptTerms;
import com.globalpaysolutions.yocomprorecarga.ui.activities.TokenInput;
import com.globalpaysolutions.yocomprorecarga.ui.activities.ValidatePhone;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.HomeView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public class HomePresenterImpl implements IHomePresenter, HomeListener, LocationCallback
{
    private static final String TAG = HomePresenterImpl.class.getSimpleName();

    private HomeView mView;
    private Context mContext;
    private UserData mUserData;
    private Activity mActivity;
    private HomeInteractor mInteractor;

    private GoogleLocationApiManager mGoogleLocationApiManager;

    public HomePresenterImpl(HomeView pView, AppCompatActivity pActivity, Context pContext)
    {
        mView = pView;
        mContext = pContext;
        mUserData = new UserData(mContext);
        mActivity = pActivity;
        mInteractor = new HomeInteractor(mContext, this);

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
    public void intializeGeolocation()
    {
        mInteractor.intializeGeolocation();
    }

    @Override
    public void salesPointsQuery(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        mInteractor.salesPointsQuery(location);
    }

    @Override
    public void updateSalePntCriteria(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        mInteractor.salesPointsUpdateCriteria(location);
    }

    @Override
    public void vendorPointsQuery(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        mInteractor.vendorPointsQuery(location);
    }

    @Override
    public void updateVendorePntCriteria(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        mInteractor.vendorPointsUpdateCriteria(location);
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


   /*
   *
   *   HomeListener
   *
   */

    // GEOFIRE STATIC POINTS
    @Override
    public void gf_salePoint_onKeyEntered(String pKey, LatLng pLocation)
    {
        mView.addSalePoint(pKey, pLocation );
    }

    @Override
    public void gf_salePoint_onKeyExited(String pKey)
    {
        mView.removeSalePoint(pKey);
    }

    // GEOFIRE VENDOR POINTS
    @Override
    public void gf_vendorPoint_onKeyEntered(String pKey, LatLng pLocation)
    {
        mView.addVendorPoint(pKey, pLocation);
    }

    @Override
    public void gf_vendorPoint_onKeyExited(String pKey)
    {
        mView.removeVendorPoint(pKey);
    }

    @Override
    public void gf_vendorPoint_onKeyMoved(String pKey, LatLng pLocation)
    {
        mView.moveVendorPoint(pKey, pLocation);
    }

    @Override
    public void gf_vendorPoint_onGeoQueryReady()
    {

    }

    @Override
    public void gf_vendorPoint_onGeoQueryError(DatabaseError pError)
    {

    }

    // FIREBASE STATIC POINTS
    @Override
    public void fb_salePoint_onDataChange(String pKey, SalePointData pSalePointData)
    {
        mView.addSalePointData(pKey, pSalePointData.getTitle(), pSalePointData.getSnippet());
    }

    @Override
    public void fb_salePoint_onCancelled(DatabaseError databaseError)
    {
        Log.e(TAG, "fb_salePoint_onCancelled -" + databaseError.getMessage());
    }

    // FIREBASE VENDOR POINTS
    @Override
    public void fb_vendorPoint_onDataChange(String pKey, VendorPointData pSalePointData)
    {
        //TODO: Quitar el parámetro del metodo
        mView.addVendorPointData(pKey, "¡YoVendoRecarga!", pSalePointData.getVendorCode());
    }

    @Override
    public void fb_vendorPoint_onCancelled(DatabaseError databaseError)
    {

    }
}
