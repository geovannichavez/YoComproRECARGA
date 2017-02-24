package com.globalpaysolutions.yocomprorecarga.interactors;

import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Josué Chávez on 24/02/2017.
 */

public class HomeInteractor implements GeoQueryEventListener
{
    @Override
    public void onKeyEntered(String key, GeoLocation location)
    {

    }

    @Override
    public void onKeyExited(String key)
    {

    }

    @Override
    public void onKeyMoved(String key, GeoLocation location)
    {

    }

    @Override
    public void onGeoQueryReady()
    {

    }

    @Override
    public void onGeoQueryError(DatabaseError error)
    {

    }
}
