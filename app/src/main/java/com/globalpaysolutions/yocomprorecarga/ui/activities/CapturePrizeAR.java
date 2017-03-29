package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.IOException;

public class CapturePrizeAR extends AppCompatActivity
{
    private ArchitectView architectView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_price_ar);
        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);

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
            this.architectView.load("YOUR-AR-URL");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
