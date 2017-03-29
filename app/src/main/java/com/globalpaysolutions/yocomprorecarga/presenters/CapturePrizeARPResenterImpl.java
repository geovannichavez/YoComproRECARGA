package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.location.LocationCallback;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICapturePrizeARPresenter;
import com.globalpaysolutions.yocomprorecarga.views.CapturePrizeView;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public class CapturePrizeARPResenterImpl implements ICapturePrizeARPresenter, LocationCallback
{
    Context mContext;
    CapturePrizeView mView;


    public CapturePrizeARPResenterImpl(Context pContext, AppCompatActivity pActivity, CapturePrizeView pView)
    {
        this.mContext = pContext;
        mView = pView;
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void onLocationChanged(Location location)
    {
        
    }

    @Override
    public void onLocationApiManagerConnected(Location location)
    {

    }
}
