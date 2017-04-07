package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.presenters.CapturePrizeARPResenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;
import com.globalpaysolutions.yocomprorecarga.views.CapturePrizeView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.IOException;

public class CapturePrizeAR extends AppCompatActivity implements CapturePrizeView
{
    private static final String TAG = CapturePrizeAR.class.getSimpleName();
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
        mPresenter.initialize();

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
    public void updateUserLocation(double pLatitude, double pLongitude, double pAccuracy)
    {
        LatLng location = new LatLng(pLatitude, pLongitude);
        this.mPresenter.updatePrizePntCriteria(location);
        this.architectView.setLocation(pLatitude, pLongitude, pAccuracy);
    }

    @Override
    public void locationManagerConnected(double pLatitude, double pLongitude, double pAccuracy)
    {
        LatLng location = new LatLng(pLatitude, pLongitude);
        this.mPresenter.prizePointsQuery(location);
        this.architectView.setLocation(pLatitude, pLongitude, pAccuracy);

    }

    @Override
    public void onPOIClick()
    {
        try
        {
            this.architectView.registerUrlListener(new ArchitectView.ArchitectUrlListener()
            {
                @Override
                public boolean urlWasInvoked(String s)
                {
                    switch (s)
                    {
                        case StringsURL.ARCH_GOLD:
                            mPresenter._genericPOIAction("Oro");
                            break;
                        case StringsURL.ARCH_SILVER:
                            mPresenter._genericPOIAction("Plata");
                            break;
                        case StringsURL.ARCH_BRONZE:
                            mPresenter._genericPOIAction("Bronce");
                            break;
                        default:
                            Log.i(TAG, "No Wikitude-ArchitectView URL Provided");
                            break;
                    }
                    return false;
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showGenericDialog(DialogViewModel pMessageModel)
    {
        CreateDialog(pMessageModel.getTitle(), pMessageModel.getLine1(), pMessageModel.getAcceptButton());
    }

    @Override
    public void showIncompatibleDeviceDialog(DialogViewModel pMessageModel)
    {
        String message = String.format("%1$s %2$s.", pMessageModel.getLine1(), pMessageModel.getLine2());

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CapturePrizeAR.this);
        alertDialog.setTitle(pMessageModel.getTitle());
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(pMessageModel.getAcceptButton(), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent returnHome = new Intent(CapturePrizeAR.this, Home.class);
                returnHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                returnHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                returnHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                returnHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                returnHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                returnHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(returnHome);
            }
        });
        alertDialog.show();
    }

    @Override
    public void onGoldKeyEntered(String pKey, LatLng pLocation)
    {
        this.architectView.callJavascript
                ("World.createModelGoldAtLocation(" + pLocation.latitude + ", " + pLocation.longitude + ", '" + pKey + "')");
    }

    @Override
    public void onGoldKeyExited(String pKey)
    {

    }

    @Override
    public void onGoldPointDataChange(String pKey, LocationPrizeYCRData pGoldPointData)
    {

    }

    @Override
    public void onGoldPointCancelled(DatabaseError pDatabaseError)
    {

    }

    @Override
    public void onSilverKeyEntered(String pKey, LatLng pLocation)
    {
        this.architectView.callJavascript
                ("World.createModelSilverAtLocation(" + pLocation.latitude + ", " + pLocation.longitude + ", '" + pKey + "')");
    }

    @Override
    public void onSilverKeyExited(String pKey)
    {

    }

    @Override
    public void onSilverPointDataChange(String pKey, LocationPrizeYCRData pGoldPointData)
    {

    }

    @Override
    public void onSilverPointCancelled(DatabaseError pDatabaseError)
    {

    }

    @Override
    public void onBronzeKeyEntered(String pKey, LatLng pLocation)
    {
        this.architectView.callJavascript
                ("World.createModelBronzeAtLocation(" + pLocation.latitude + ", " + pLocation.longitude + ", '" + pKey + "')");

    }

    @Override
    public void onBronzeKeyExited(String pKey)
    {

    }

    @Override
    public void onBronzePointDataChange(String pKey, LocationPrizeYCRData pGoldPointData)
    {

    }

    @Override
    public void onBronzePointCancelled(DatabaseError pDatabaseError)
    {

    }


    /*
    *
    *   ACTIVITY LIFECYCLES
    *
    */
    @Override
    protected void onResume()
    {
        super.onResume();
        architectView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        architectView.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        architectView.onDestroy();
    }

    /*
    *
    *
    * OTROS METODOS
    *
    */

    public void CreateDialog(String pTitle, String pMessage, String pButton)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CapturePrizeAR.this);
        alertDialog.setTitle(pTitle);
        alertDialog.setMessage(pMessage);
        alertDialog.setPositiveButton(pButton, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
