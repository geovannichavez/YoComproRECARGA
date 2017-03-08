package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IHomeInteractor;
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

    //GeoFire
    private GeoFire mSalesPntsRef;
    private GeoFire mVendorPntsRef;
    private GeoQuery mSalesPntsQuery;
    private GeoQuery mVendorPntsQuery;

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


}
