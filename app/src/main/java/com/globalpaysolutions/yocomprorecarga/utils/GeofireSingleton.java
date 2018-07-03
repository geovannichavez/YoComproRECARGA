package com.globalpaysolutions.yocomprorecarga.utils;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoQuery;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Josué Chávez on 31/01/2018.
 */

public class GeofireSingleton
{
    private static final String TAG = GeofireSingleton.class.getSimpleName();

    private static GeofireSingleton mSingleton;
    //References
    private static GeoFire mGoldPointsRef;
    private static GeoFire mSilverPointsRef;
    private static GeoFire mBronzePointsRef;
    private static GeoFire mWildcardPointsRef;
    private static GeoFire mSponsorPrizeRef;

    //Queries
    private static GeoQuery mGoldPointsQuery;
    private static GeoQuery mSilverPointsQuery;
    private static GeoQuery mBronzePointsQuery;
    private static GeoQuery mWildcardPointsQuery;
    private static GeoQuery mSponsorPrizeQuery;

    private GeofireSingleton()
    {

    }

    public static synchronized GeofireSingleton getInstance()
    {
        if (mSingleton == null)
        {
            mSingleton = new GeofireSingleton();
        }
        return mSingleton;
    }

    public void initializeReferences(DatabaseReference goldReference, DatabaseReference silverReference,
                                     DatabaseReference bronzeReference, DatabaseReference wildcardReference, DatabaseReference sponsorPrizeReference)
    {
        try
        {
            if(mGoldPointsRef == null)
                mGoldPointsRef = new GeoFire(goldReference);

            if(mSilverPointsRef == null)
                mSilverPointsRef = new GeoFire(silverReference);

            if(mBronzePointsRef == null)
                mBronzePointsRef = new GeoFire(bronzeReference);

            if(mWildcardPointsRef == null)
                mWildcardPointsRef = new GeoFire(wildcardReference);

            if(mSponsorPrizeRef == null)
                mSponsorPrizeRef = new GeoFire(sponsorPrizeReference);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Geofire references could not be initialized: " + ex.getMessage());
        }
    }

    public GeoFire getGoldPointReference()
    {
        return mGoldPointsRef;
    }

    public GeoFire getSilverPointReference()
    {
        return mSilverPointsRef;
    }

    public GeoFire getBronzePointReference()
    {
        return mBronzePointsRef;
    }

    public GeoFire getWildcardPointsRef()
    {
        return mWildcardPointsRef;
    }


    public GeoFire getSponsorPrizeRef()
    {
        return mSponsorPrizeRef;
    }
}
