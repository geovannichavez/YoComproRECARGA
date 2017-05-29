package com.globalpaysolutions.yocomprorecarga.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.FirebasePOIInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.FirebasePOIListener;
import com.globalpaysolutions.yocomprorecarga.interactors.HomeInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.HomeListener;
import com.globalpaysolutions.yocomprorecarga.location.GoogleLocationApiManager;
import com.globalpaysolutions.yocomprorecarga.location.LocationCallback;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.SalePointData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.VendorPointData;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IHomePresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.AcceptTerms;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Permissions;
import com.globalpaysolutions.yocomprorecarga.ui.activities.TokenInput;
import com.globalpaysolutions.yocomprorecarga.ui.activities.ValidatePhone;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.HomeView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public class HomePresenterImpl implements IHomePresenter, HomeListener, FirebasePOIListener, LocationCallback
{
    private static final String TAG = HomePresenterImpl.class.getSimpleName();

    private HomeView mView;
    private Context mContext;
    private UserData mUserData;
    private Activity mActivity;
    private HomeInteractor mInteractor;
    private FirebasePOIInteractor mFirebaseInteractor;

    private GoogleLocationApiManager mGoogleLocationApiManager;

    public HomePresenterImpl(HomeView pView, AppCompatActivity pActivity, Context pContext)
    {
        mView = pView;
        mContext = pContext;
        mUserData = new UserData(mContext);
        mActivity = pActivity;
        mInteractor = new HomeInteractor(mContext, this);
        mFirebaseInteractor = new FirebasePOIInteractor(mContext, this);

        this.mGoogleLocationApiManager = new GoogleLocationApiManager(pActivity, mContext);
        this.mGoogleLocationApiManager.setLocationCallback(this);

    }

    @Override
    public void setInitialViewsState()
    {
        this.mView.renderMap();
    }

    @Override
    public void checkUserDataCompleted()
    {
        if(!mUserData.UserAcceptedTerms())
        {
            Intent acceptTerms = new Intent(mActivity, AcceptTerms.class);
            mContext.startActivity(acceptTerms);
        }
        else if(!mUserData.UserGrantedDevicePermissions())
        {
            Intent permissions = new Intent(mActivity, Permissions.class);
            this.addFlags(permissions);
            mContext.startActivity(permissions);
        }
        else if (!mUserData.UserSelectedCountry())
        {
            Intent selectCountry = new Intent(mActivity, ValidatePhone.class);
            this.addFlags(selectCountry);
            mContext.startActivity(selectCountry);
        }
        else if(!mUserData.UserVerifiedPhone())
        {
            Intent inputToken = new Intent(mActivity, TokenInput.class);
            this.addFlags(inputToken);
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
    public void sendStoreAirtimeReport(String pStoreName, String pAddress, LatLng pLocation, String pFirebaseID)
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        double longitide = pLocation.longitude;
        double latitude = pLocation.latitude;
        mInteractor.sendStoreAirtimeReport(pStoreName, pAddress, longitide, latitude, pFirebaseID);
    }

    @Override
    public void onSalePointClick(String pStoreName, String pAddress, LatLng pLocation, String pFirebaseID)
    {
        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(mContext.getString(R.string.dialog_title_airtime_report));
        dialog.setLine1(mContext.getString(R.string.dialog_content_airtime_report));
        dialog.setAcceptButton(mContext.getString(R.string.button_yes));
        dialog.setCanelButton(mContext.getString(R.string.button_no));
        mView.showCustomStoreReportDialog(dialog, pStoreName, pAddress, pLocation, pFirebaseID);
    }

    @Override
    public void intializeGeolocation()
    {
        mInteractor.initializeGeolocation();
        mFirebaseInteractor.initializePOIGeolocation();
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
    public void prizePointsQuery(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        mFirebaseInteractor.goldPointsQuery(location, Constants.PRIZES_STOP_RADIUS_KM);
        mFirebaseInteractor.silverPointsQuery(location, Constants.PRIZES_STOP_RADIUS_KM);
        mFirebaseInteractor.bronzePointsQuery(location, Constants.PRIZES_STOP_RADIUS_KM);
    }

    @Override
    public void updatePrizePntCriteria(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        mFirebaseInteractor.goldPointsUpdateCriteria(location, Constants.PRIZES_STOP_RADIUS_KM);
        mFirebaseInteractor.silverPointsUpdateCriteria(location, Constants.PRIZES_STOP_RADIUS_KM);
        mFirebaseInteractor.bronzePointsUpdateCriteria(location, Constants.PRIZES_STOP_RADIUS_KM);
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

    @Override
    public void onStoreAirtimeReportSuccess(SimpleMessageResponse pResponse)
    {
        mView.hideLoadingDialog();
        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(mContext.getString(R.string.title_success_dialog_store_report));
        dialog.setLine1(mContext.getString(R.string.dialog_content_report_sent));
        dialog.setAcceptButton(mContext.getString(R.string.button_accept));
        mView.showSuccessMessage(dialog);
    }

    @Override
    public void onError(int pCodeStatus, Throwable pThrowable)
    {
        mView.hideLoadingDialog();
        ProcessErrorMessage(pCodeStatus, pThrowable);
    }

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

    @Override
    public void gf_goldPoint_onKeyEntered(String pKey, LatLng pLocation, boolean p3DCompatible)
    {
        mView.addGoldPoint(pKey, pLocation);
    }

    @Override
    public void gf_goldPoint_onKeyExited(String pKey, boolean p3DCompatible)
    {
        mView.removeGoldPoint(pKey);
    }

    @Override
    public void gf_silverPoint_onKeyEntered(String pKey, LatLng pLocation,  boolean p3DCompatible)
    {
        mView.addSilverPoint(pKey, pLocation);
    }

    @Override
    public void gf_silverPoint_onKeyExited(String pKey, boolean p3DCompatible)
    {
        mView.removeSilverPoint(pKey);
    }

    @Override
    public void gf_bronzePoint_onKeyEntered(String pKey, LatLng pLocation,  boolean p3DCompatible)
    {
        mView.addBronzePoint(pKey, pLocation);
    }

    @Override
    public void gf_bronzePoint_onKeyExited(String pKey, boolean p3DCompatible)
    {
        mView.removeBronzePoint(pKey);
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
        if(pSalePointData != null)
            //TODO: Quitar el parámetro del metodo
            mView.addVendorPointData(pKey, "¡YoVendoRecarga!", pSalePointData.getVendorCode());
    }

    @Override
    public void fb_vendorPoint_onCancelled(DatabaseError databaseError)
    {

    }

    //  FIREBASE GOLD POINTS
    @Override
    public void fb_goldPoint_onDataChange(String pKey, LocationPrizeYCRData pGoldPointData)
    {
        if(pGoldPointData != null)
            mView.addGoldPointData(pKey, pGoldPointData.getCoins(), pGoldPointData.getDetail());
    }

    @Override
    public void fb_goldPoint_onCancelled(DatabaseError databaseError)
    {
        Log.e(TAG, "GoldPoint DatabaseError: OnCancelled Fired!");
    }

    //  FIREBASE SILVER POINTS
    @Override
    public void fb_silverPoint_onDataChange(String pKey, LocationPrizeYCRData pSilverPointData)
    {
        if(pSilverPointData != null)
            mView.addSilverPointData(pKey, pSilverPointData.getCoins(), pSilverPointData.getDetail());
    }

    @Override
    public void fb_silverPoint_onCancelled(DatabaseError databaseError)
    {
        Log.e(TAG, "SilverPoint DatabaseError: OnCancelled Fired!");
    }

    @Override
    public void fb_bronzePoint_onDataChange(String pKey, LocationPrizeYCRData pBronzePointData)
    {
        if(pBronzePointData != null)
            mView.addBronzePointData(pKey, pBronzePointData.getCoins(), pBronzePointData.getDetail());
    }

    @Override
    public void fb_bronzePoint_onCancelled(DatabaseError databaseError)
    {
        Log.e(TAG, "BronzePoint DatabaseError: OnCancelled Fired!");
    }

    /*
    *
    *
    *   OTROS METODOS
    *
    *
    */

    private void addFlags(Intent pIntent)
    {
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
    }

    private void ProcessErrorMessage(int pCodeStatus, Throwable pThrowable)
    {
        DialogViewModel errorResponse = new DialogViewModel();

        try
        {
            String Titulo;
            String Linea1;
            String Button;

            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
                else if (pThrowable instanceof IOException)
                {
                    Titulo = mContext.getString(R.string.error_title_internet_connecttion);
                    Linea1 = mContext.getString(R.string.error_content_internet_connecttion);
                    Button = mContext.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
            }
            else
            {

                Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                Button = mContext.getString(R.string.button_accept);

            }

            errorResponse.setTitle(Titulo);
            errorResponse.setLine1(Linea1);
            errorResponse.setAcceptButton(Button);
            this.mView.showErrorMessage(errorResponse);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
