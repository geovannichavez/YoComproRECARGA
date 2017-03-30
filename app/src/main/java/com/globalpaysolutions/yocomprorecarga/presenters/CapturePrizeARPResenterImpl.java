package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.location.GoogleLocationApiManager;
import com.globalpaysolutions.yocomprorecarga.location.LocationCallback;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICapturePrizeARPresenter;
import com.globalpaysolutions.yocomprorecarga.views.CapturePrizeView;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public class CapturePrizeARPResenterImpl implements ICapturePrizeARPresenter, LocationCallback
{
    private static final String TAG = CapturePrizeARPResenterImpl.class.getSimpleName();

    private Context mContext;
    private AppCompatActivity mActivity;
    private CapturePrizeView mView;

    private GoogleLocationApiManager mGoogleLocationApiManager;

    public CapturePrizeARPResenterImpl(Context pContext, AppCompatActivity pActivity, CapturePrizeView pView)
    {
        this.mContext = pContext;
        this.mActivity = pActivity;
        this.mView = pView;

        this.mGoogleLocationApiManager = new GoogleLocationApiManager(mActivity, mContext);
        this.mGoogleLocationApiManager.setLocationCallback(this);

    }

    @Override
    public void initialize()
    {
        if(!this.mGoogleLocationApiManager.isConnectionEstablished())
            this.mGoogleLocationApiManager.connect();
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
}
