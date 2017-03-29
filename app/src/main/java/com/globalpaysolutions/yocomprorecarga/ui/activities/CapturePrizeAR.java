package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.CapturePrizeARPResenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.views.CapturePrizeView;
import com.google.android.gms.maps.model.LatLng;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.IOException;

public class CapturePrizeAR extends AppCompatActivity implements CapturePrizeView
{
    private ArchitectView architectView;

    //MVP
    CapturePrizeARPResenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_price_ar);
        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);

        mPresenter = new CapturePrizeARPResenterImpl(this, this, this);

        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey(Constants.WIKITUDE_LICENSE_KEY);
        this.architectView.onCreate(config);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        this.architectView.onPostCreate();
        try
        {
            this.architectView.load("index.html");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void updateUserLocation(LatLng pLocation)
    {

    }

    @Override
    public void locationManagerConnected(LatLng pLocation)
    {

    }
}
