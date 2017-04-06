package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.FirebasePOIInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.FirebasePOIListener;
import com.globalpaysolutions.yocomprorecarga.location.GoogleLocationApiManager;
import com.globalpaysolutions.yocomprorecarga.location.LocationCallback;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICapturePrizeARPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.views.CapturePrizeView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public class CapturePrizeARPResenterImpl implements ICapturePrizeARPresenter, FirebasePOIListener, LocationCallback
{
    private static final String TAG = CapturePrizeARPResenterImpl.class.getSimpleName();

    private Context mContext;
    private AppCompatActivity mActivity;
    private CapturePrizeView mView;

    private GoogleLocationApiManager mGoogleLocationApiManager;
    private FirebasePOIInteractor mFirebaseInteractor;

    public CapturePrizeARPResenterImpl(Context pContext, AppCompatActivity pActivity, CapturePrizeView pView)
    {
        this.mContext = pContext;
        this.mActivity = pActivity;
        this.mView = pView;

        this.mGoogleLocationApiManager = new GoogleLocationApiManager(mActivity, mContext);
        this.mGoogleLocationApiManager.setLocationCallback(this);

        this.mFirebaseInteractor = new FirebasePOIInteractor(mContext, this);

    }

    @Override
    public void initialize()
    {
        if(!this.mGoogleLocationApiManager.isConnectionEstablished())
            this.mGoogleLocationApiManager.connect();

        mFirebaseInteractor.initializePOIGeolocation();
    }

    @Override
    public void prizePointsQuery(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        this.mFirebaseInteractor.goldPointsQuery(location, Constants.AR_POI_RADIOS_KM);
        this.mFirebaseInteractor.silverPointsQuery(location, Constants.AR_POI_RADIOS_KM);
        this.mFirebaseInteractor.bronzePointsQuery(location, Constants.AR_POI_RADIOS_KM);
    }

    @Override
    public void updatePrizePntCriteria(LatLng pLocation)
    {
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        this.mFirebaseInteractor.goldPointsUpdateCriteria(location, Constants.AR_POI_RADIOS_KM);
        this.mFirebaseInteractor.silverPointsUpdateCriteria(location, Constants.AR_POI_RADIOS_KM);
        this.mFirebaseInteractor.bronzePointsUpdateCriteria(location, Constants.AR_POI_RADIOS_KM);
    }

    @Override
    public void setPOIClickListener()
    {
        mView.onPOIClick();
    }

    @Override
    public void _genericPOIAction(String pDisplayText)
    {
        DialogViewModel dialog  = new DialogViewModel();
        dialog.setTitle(String.format("Punto de %1$s", pDisplayText));
        dialog.setLine1(String.format("Ha tocado el Punto de %1$s", pDisplayText));
        dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
        mView.showGenericDialog(dialog);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        mView.updateUserLocation(location.getLatitude(), location.getLongitude(), location.getAccuracy());
    }

    @Override
    public void onLocationApiManagerConnected(Location location)
    {
        mView.locationManagerConnected(location.getLatitude(), location.getLongitude(), location.getAccuracy());
    }

    @Override
    public void gf_goldPoint_onKeyEntered(String pKey, LatLng pLocation)
    {
        mView.onGoldKeyEntered(pKey, pLocation);
    }

    @Override
    public void gf_goldPoint_onKeyExited(String pKey)
    {
        mView.onGoldKeyExited(pKey);
    }

    @Override
    public void gf_silverPoint_onKeyEntered(String pKey, LatLng pLocation)
    {
        mView.onSilverKeyEntered(pKey, pLocation);
    }

    @Override
    public void gf_silverPoint_onKeyExited(String pKey)
    {
        mView.onSilverKeyExited(pKey);
    }

    @Override
    public void gf_bronzePoint_onKeyEntered(String pKey, LatLng pLocation)
    {
        mView.onBronzeKeyEntered(pKey, pLocation);
    }

    @Override
    public void gf_bronzePoint_onKeyExited(String pKey)
    {
        mView.onBronzeKeyExited(pKey);
    }

    @Override
    public void fb_goldPoint_onDataChange(String pKey, LocationPrizeYCRData pGoldPointData)
    {
        mView.onGoldPointDataChange(pKey, pGoldPointData);
    }

    @Override
    public void fb_goldPoint_onCancelled(DatabaseError databaseError)
    {
        mView.onGoldPointCancelled(databaseError);
    }

    @Override
    public void fb_silverPoint_onDataChange(String pKey, LocationPrizeYCRData pSilverPointData)
    {
        mView.onSilverPointDataChange(pKey, pSilverPointData);
    }

    @Override
    public void fb_silverPoint_onCancelled(DatabaseError databaseError)
    {
        mView.onSilverPointCancelled(databaseError);
    }

    @Override
    public void fb_bronzePoint_onDataChange(String pKey, LocationPrizeYCRData pBronzePointData)
    {
        mView.onBronzePointDataChange(pKey, pBronzePointData);
    }

    @Override
    public void fb_bronzePoint_onCancelled(DatabaseError databaseError)
    {
        mView.onBronzePointCancelled(databaseError);
    }
}
