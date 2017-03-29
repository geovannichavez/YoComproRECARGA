package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IHomeInteractor;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.SalePointData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.VendorPointData;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Josué Chávez on 24/02/2017.
 */

public class HomeInteractor implements IHomeInteractor
{
    private static final String TAG = HomeInteractor.class.getSimpleName();
    private Context mContext;
    private HomeListener mHomeListener;

    //Firebase
    private DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mSalesPoints = mRootReference.child("staticPoints");
    private DatabaseReference mDataSalesPoints = mRootReference.child("dataStaticPoints");
    private DatabaseReference mVendorPoints = mRootReference.child("YVR");
    private DatabaseReference mDataVendorPoints = mRootReference.child("dataYVR");

    private DatabaseReference mGoldPoints = mRootReference.child("locationGoldYCR");
    private DatabaseReference mGoldPointsData = mRootReference.child("locationGoldYCRData");
    private DatabaseReference mSilverPoints = mRootReference.child("locationSilverYCR");
    private DatabaseReference mSilverPointsData = mRootReference.child("locationSilverYCRData");
    private DatabaseReference mBronzePoints = mRootReference.child("locationBronzeYCR");
    private DatabaseReference mBronzePointsData = mRootReference.child("locationBronzeYCRData");


    //GeoFire
    private GeoFire mSalesPntsRef;
    private GeoFire mVendorPntsRef;
    private GeoFire mGoldPointsRef;
    private GeoFire mSilverPointsRef;
    private GeoFire mBronzePointsRef;

    private GeoQuery mSalesPntsQuery;
    private GeoQuery mVendorPntsQuery;
    private GeoQuery mGoldPointsQuery;
    private GeoQuery mSilverPointsQuery;
    private GeoQuery mBronzePointsQuery;


    public HomeInteractor(Context pContext, HomeListener pListener)
    {
        mContext = pContext;
        mHomeListener = pListener;
    }


    @Override
    public void intializeGeolocation()
    {
        //GeoFire
        mSalesPntsRef = new GeoFire(mSalesPoints);
        mVendorPntsRef = new GeoFire(mVendorPoints);

        mGoldPointsRef = new GeoFire(mGoldPoints);
        mSilverPointsRef = new GeoFire(mSilverPoints);
        mBronzePointsRef = new GeoFire(mBronzePoints);
    }

    @Override
    public void salesPointsQuery(GeoLocation pLocation)
    {
        try
        {
            mSalesPntsQuery = mSalesPntsRef.queryAtLocation(pLocation, Constants.SALES_POINTS_RADIUS_KM);
            mSalesPntsQuery.addGeoQueryEventListener(salesPointsListener);
        }
        catch (IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void salesPointsUpdateCriteria(GeoLocation pLocation)
    {
        try
        {
            mSalesPntsQuery.setLocation(pLocation, Constants.SALES_POINTS_RADIUS_KM);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void vendorPointsQuery(GeoLocation pLocation)
    {
        try
        {
            mVendorPntsQuery = mVendorPntsRef.queryAtLocation(pLocation, Constants.VENDOR_RADIUS_KM);
            mVendorPntsQuery.addGeoQueryEventListener(vendorPointsListener);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void vendorPointsUpdateCriteria(GeoLocation pLocation)
    {
        try
        {
            mVendorPntsQuery.setLocation(pLocation, Constants.VENDOR_RADIUS_KM);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void goldPointsQuery(GeoLocation pLocation)
    {
        try
        {
            mGoldPointsQuery = mGoldPointsRef.queryAtLocation(pLocation, Constants.PRIZES_STOP_RADIUS_KM);
            mGoldPointsQuery.addGeoQueryEventListener(goldPointsListener);
        }
        catch (IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void goldPointsUpdateCriteria(GeoLocation pLocation)
    {
        try
        {
            mGoldPointsQuery.setLocation(pLocation, Constants.PRIZES_STOP_RADIUS_KM);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void silverPointsQuery(GeoLocation pLocation)
    {
        try
        {
            mSilverPointsQuery = mSilverPointsRef.queryAtLocation(pLocation, Constants.PRIZES_STOP_RADIUS_KM);
            mSilverPointsQuery.addGeoQueryEventListener(silverPointsListener);
        }
        catch (IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void silverPointsUpdateCriteria(GeoLocation pLocation)
    {
        try
        {
            mSilverPointsQuery.setLocation(pLocation, Constants.PRIZES_STOP_RADIUS_KM);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void bronzePointsQuery(GeoLocation pLocation)
    {
        try
        {
            mBronzePointsQuery = mBronzePointsRef.queryAtLocation(pLocation, Constants.PRIZES_STOP_RADIUS_KM);
            mBronzePointsQuery.addGeoQueryEventListener(bronzePointsListener);
        }
        catch (IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void bronzePointsUpdateCriteria(GeoLocation pLocation)
    {
        try
        {
            mBronzePointsQuery.setLocation(pLocation, Constants.PRIZES_STOP_RADIUS_KM);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /*
    *
    * LISTENERS
    *
    */

    private GeoQueryEventListener salesPointsListener = new GeoQueryEventListener()
    {
        @Override
        public void onKeyEntered(final String key, GeoLocation location)
        {
            mDataSalesPoints.child(key).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    SalePointData salePoint = dataSnapshot.getValue(SalePointData.class);
                    mHomeListener.fb_salePoint_onDataChange(key, salePoint);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mHomeListener.fb_salePoint_onCancelled(databaseError);
                }
            });

            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mHomeListener.gf_salePoint_onKeyEntered(key, geoLocation);
        }

        @Override
        public void onKeyExited(String key)
        {
            mHomeListener.gf_salePoint_onKeyExited(key);
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            Log.i(TAG, "StaticPoint: Key moved fired.");
        }

        @Override
        public void onGeoQueryReady()
        {
            Log.i(TAG, "StaticPoint: GeoQuery ready fired.");
        }

        @Override
        public void onGeoQueryError(DatabaseError error)
        {
            Log.e(TAG, "StaticPoint: GeoFire Database error fired.");
        }
    };

    private GeoQueryEventListener vendorPointsListener = new GeoQueryEventListener()
    {
        @Override
        public void onKeyEntered(final String key, GeoLocation location)
        {
            mDataVendorPoints.child(key).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    VendorPointData vendorPoint = dataSnapshot.getValue(VendorPointData.class);
                    mHomeListener.fb_vendorPoint_onDataChange(key, vendorPoint);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mHomeListener.fb_vendorPoint_onCancelled(databaseError);
                }
            });

            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mHomeListener.gf_vendorPoint_onKeyEntered(key, geoLocation);
        }

        @Override
        public void onKeyExited(String key)
        {
            mHomeListener.gf_vendorPoint_onKeyExited(key);
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mHomeListener.gf_vendorPoint_onKeyMoved(key, geoLocation);
        }

        @Override
        public void onGeoQueryReady()
        {
            mHomeListener.gf_vendorPoint_onGeoQueryReady();
            Log.i(TAG, "VendorPoint: GeoQuery ready fired.");
        }

        @Override
        public void onGeoQueryError(DatabaseError error)
        {
            mHomeListener.gf_vendorPoint_onGeoQueryError(error);
            Log.e(TAG, "VendorPoint: GeoFire Database error fired.");
        }
    };

    private GeoQueryEventListener goldPointsListener = new GeoQueryEventListener()
    {

        @Override
        public void onKeyEntered(final String key, GeoLocation location)
        {
            mGoldPointsData.child(key).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    LocationPrizeYCRData goldPoint = dataSnapshot.getValue(LocationPrizeYCRData.class);
                    mHomeListener.fb_goldPoint_onDataChange(key, goldPoint);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mHomeListener.fb_goldPoint_onCancelled(databaseError);
                }
            });

            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mHomeListener.gf_goldPoint_onKeyEntered(key, geoLocation);
        }

        @Override
        public void onKeyExited(String key)
        {
            mHomeListener.gf_goldPoint_onKeyExited(key);
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            Log.i(TAG, "GoldPoint: Key moved fired.");
        }

        @Override
        public void onGeoQueryReady()
        {
            Log.i(TAG, "GoldPoint: GeoQuery ready fired.");
        }

        @Override
        public void onGeoQueryError(DatabaseError error)
        {
            Log.e(TAG, "GoldPoint: GeoFire Database error fired.");
        }
    };

    private GeoQueryEventListener silverPointsListener = new GeoQueryEventListener()
    {

        @Override
        public void onKeyEntered(final String key, GeoLocation location)
        {
            mSilverPointsData.child(key).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    LocationPrizeYCRData silverPoint = dataSnapshot.getValue(LocationPrizeYCRData.class);
                    mHomeListener.fb_silverPoint_onDataChange(key, silverPoint);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mHomeListener.fb_silverPoint_onCancelled(databaseError);
                }
            });

            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mHomeListener.gf_silverPoint_onKeyEntered(key, geoLocation);
        }

        @Override
        public void onKeyExited(String key)
        {
            mHomeListener.gf_silverPoint_onKeyExited(key);
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            Log.i(TAG, "SilverPoint: Key moved fired");
        }

        @Override
        public void onGeoQueryReady()
        {
            Log.i(TAG, "SilverPoint: GeoQuery ready fired");
        }

        @Override
        public void onGeoQueryError(DatabaseError error)
        {
            Log.e(TAG, "SilverPoint: Firebase DatabaseError fired");
        }
    };

    private GeoQueryEventListener bronzePointsListener = new GeoQueryEventListener()
    {

        @Override
        public void onKeyEntered(final String key, GeoLocation location)
        {
            mBronzePointsData.child(key).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    LocationPrizeYCRData bronzePoint = dataSnapshot.getValue(LocationPrizeYCRData.class);
                    mHomeListener.fb_bronzePoint_onDataChange(key, bronzePoint);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mHomeListener.fb_bronzePoint_onCancelled(databaseError);
                }
            });

            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mHomeListener.gf_bronzePoint_onKeyEntered(key, geoLocation);
        }

        @Override
        public void onKeyExited(String key)
        {
            mHomeListener.gf_bronzePoint_onKeyExited(key);
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            Log.i(TAG, "BronzePoint: Warning, bronze point key moved fired!");
        }

        @Override
        public void onGeoQueryReady()
        {
            Log.i(TAG, "BronzePoint: GeoQuery for BronzePoint ready");
        }

        @Override
        public void onGeoQueryError(DatabaseError error)
        {
            Log.e(TAG, "BronzePoint: DatabaseError for BronzePoint fired");
        }
    };


}
