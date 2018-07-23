package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IFirebasePOIInteractor;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.SponsorPrizeData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.WildcardYCRData;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Josué Chávez on 31/03/2017.
 */

public class FirebasePOIInteractor implements IFirebasePOIInteractor
{
    private static final String TAG = FirebasePOIInteractor.class.getSimpleName();
    private Context mContext;
    private FirebasePOIListener mFirebaseListener;

    private UserData mUserData;

    //Firebase
    private DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mGoldPoints = mRootReference.child("locationGoldYCR");
    private DatabaseReference mGoldPointsData = mRootReference.child("locationGoldYCRData");

    private DatabaseReference mSilverPoints = mRootReference.child("locationSilverYCR");
    private DatabaseReference mSilverPointsData = mRootReference.child("locationSilverYCRData");

    private DatabaseReference mBronzePoints = mRootReference.child("locationBronzeYCR");
    private DatabaseReference mBronzePointsData = mRootReference.child("locationBronzeYCRData");

    private DatabaseReference mWildcardPoints = mRootReference.child("locationWildcardYCR");
    private DatabaseReference mWildcardPointsData = mRootReference.child("locationWildcardYCRData");

    private DatabaseReference mPlayerRecarGO = mRootReference.child("locationPlayerRecargo");
    private DatabaseReference mPlayerRecarGOData = mRootReference.child("locationPlayerRecargoData");

    private DatabaseReference mSponsorPrize = mRootReference.child("locationSponsorYCR");
    private DatabaseReference mSponsorPrizeData = mRootReference.child("locationSponsorYCRData");

    //References
    private  GeoFire mGoldPointsRef;
    private  GeoFire mSilverPointsRef;
    private  GeoFire mBronzePointsRef;
    private  GeoFire mWildcardPointsRef;
    private  GeoFire mSponsorPointsRef;


    //GeoFire Queries
    private  GeoQuery mGoldPointsQuery;
    private  GeoQuery mSilverPointsQuery;
    private  GeoQuery mBronzePointsQuery;
    private  GeoQuery mWildcardPointsQuery;
    private  GeoQuery mSponsorPrizeQuery;

    public FirebasePOIInteractor(Context pContext, FirebasePOIListener pListener)
    {
        this.mContext = pContext;
        this.mFirebaseListener = pListener;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void initializePOIGeolocation()
    {
        try
        {
            /*mGoldPointsRef = new GeoFire(mGoldPoints);
            mSilverPointsRef = new GeoFire(mSilverPoints);
            mBronzePointsRef = new GeoFire(mBronzePoints);
            mWildcardPointsRef = new GeoFire(mWildcardPoints);*/
            new InitializeGeolocation().execute();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void goldPointsQuery(GeoLocation pLocation, double pRadius)
    {
        new ExecuteGoldPointsQuery(pLocation, pRadius).execute();
    }

    @Override
    public void goldPointsUpdateCriteria(GeoLocation pLocation, double pRadius)
    {
        try
        {
            mGoldPointsQuery.setLocation(pLocation, pRadius);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void silverPointsQuery(GeoLocation pLocation, double pRadius)
    {
        new ExecuteSilverPointsQuery(pLocation, pRadius).execute();
    }

    @Override
    public void silverPointsUpdateCriteria(GeoLocation pLocation, double pRadius)
    {
        try
        {
            mSilverPointsQuery.setLocation(pLocation, pRadius);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void bronzePointsQuery(GeoLocation pLocation, double pRadius)
    {
        new ExecuteBronzePointsQuery(pLocation, pRadius).execute();
    }

    @Override
    public void bronzePointsUpdateCriteria(GeoLocation pLocation, double pRadius)
    {
        try
        {
            mBronzePointsQuery.setLocation(pLocation, pRadius);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void wildcardPointsQuery(GeoLocation location, double radius)
    {
        mWildcardPointsQuery = mWildcardPointsRef.queryAtLocation(location, radius);
        mWildcardPointsQuery.addGeoQueryEventListener(wildcardPointsListener);
    }

    @Override
    public void wildcardPointsUpdateCriteria(GeoLocation pLocation, double pRadius)
    {
        try
        {
            mWildcardPointsQuery.setLocation(pLocation, pRadius);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void sponsorPrizeQuery(GeoLocation location, double radius)
    {
        mSponsorPrizeQuery = mSponsorPointsRef.queryAtLocation(location, radius);
        mSponsorPrizeQuery.addGeoQueryEventListener(sponsorPrizeListener);
    }

    @Override
    public void sponsorPrizeQueryUpdateCriteria(GeoLocation location, double radius)
    {
        try
        {
            mSponsorPrizeQuery.setLocation(location, radius);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void retrieveSponsorPrizeData(final String key, final LatLng location)
    {
        try
        {
            mSponsorPrizeData.child(key).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    SponsorPrizeData sponsorData = dataSnapshot.getValue(SponsorPrizeData.class);
                    mFirebaseListener.fb_sponsorPrize_onDataChange(key, location, sponsorData);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mFirebaseListener.fb_sponsorPrize_onCancelled(databaseError);
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void detachFirebaseListeners()
    {
        try
        {
            mGoldPointsQuery.removeGeoQueryEventListener(goldPointsListener);
            mSilverPointsQuery.removeGeoQueryEventListener(silverPointsListener);
            mBronzePointsQuery.removeGeoQueryEventListener(bronzePointsListener);
            mWildcardPointsQuery.removeGeoQueryEventListener(wildcardPointsListener);
            mSponsorPrizeQuery.removeGeoQueryEventListener(sponsorPrizeListener);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Could not detach GeoQuery Event Listeners");
        }
    }

    /*
    *
    *
    *   GEOQUERY EVENTS LISTENER
    *
    *
    * */
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
                    mFirebaseListener.fb_goldPoint_onDataChange(key, goldPoint);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mFirebaseListener.fb_goldPoint_onCancelled(databaseError);
                }
            });

            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mFirebaseListener.gf_goldPoint_onKeyEntered(key, geoLocation, mUserData.Is3DCompatibleDevice());
        }

        @Override
        public void onKeyExited(String key)
        {
            mFirebaseListener.gf_goldPoint_onKeyExited(key, mUserData.Is3DCompatibleDevice());
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            Log.i(TAG, "GoldPoint: Key moved fired.");
        }

        @Override
        public void onGeoQueryReady()
        {
            mFirebaseListener.gf_goldPoint_onGeoQueryReady();
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
                    mFirebaseListener.fb_silverPoint_onDataChange(key, silverPoint);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mFirebaseListener.fb_silverPoint_onCancelled(databaseError);
                }
            });

            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mFirebaseListener.gf_silverPoint_onKeyEntered(key, geoLocation, mUserData.Is3DCompatibleDevice());
        }

        @Override
        public void onKeyExited(String key)
        {
            mFirebaseListener.gf_silverPoint_onKeyExited(key, mUserData.Is3DCompatibleDevice());
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            Log.i(TAG, "SilverPoint: Key moved fired");
        }

        @Override
        public void onGeoQueryReady()
        {
            mFirebaseListener.gf_silverPoint_onGeoQueryReady();
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
                    mFirebaseListener.fb_bronzePoint_onDataChange(key, bronzePoint);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mFirebaseListener.fb_bronzePoint_onCancelled(databaseError);
                }
            });

            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mFirebaseListener.gf_bronzePoint_onKeyEntered(key, geoLocation, mUserData.Is3DCompatibleDevice());
        }

        @Override
        public void onKeyExited(String key)
        {
            mFirebaseListener.gf_bronzePoint_onKeyExited(key, mUserData.Is3DCompatibleDevice());
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            Log.i(TAG, "BronzePoint: Warning, bronze point key moved fired!");
        }

        @Override
        public void onGeoQueryReady()
        {
            mFirebaseListener.gf_bronzePoint_onGeoQueryReady();
            Log.i(TAG, "BronzePoint: GeoQuery for BronzePoint ready");
        }

        @Override
        public void onGeoQueryError(DatabaseError error)
        {
            Log.e(TAG, "BronzePoint: DatabaseError for BronzePoint fired");
        }
    };

    private GeoQueryEventListener wildcardPointsListener = new GeoQueryEventListener()
    {

        @Override
        public void onKeyEntered(final String key, GeoLocation location)
        {
            mWildcardPointsData.child(key).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    WildcardYCRData wildcardYCRData = dataSnapshot.getValue(WildcardYCRData.class);
                    mFirebaseListener.fb_wildcardPoint_onDataChange(key, wildcardYCRData);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mFirebaseListener.fb_wildcardPoint_onCancelled(databaseError);
                }
            });

            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mFirebaseListener.gf_wildcardPoint_onKeyEntered(key, geoLocation, mUserData.Is3DCompatibleDevice());
        }

        @Override
        public void onKeyExited(String key)
        {
            mFirebaseListener.gf_wildcardPoint_onKeyExited(key, mUserData.Is3DCompatibleDevice());
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            Log.i(TAG, "Wildcard: Warning, wildcard point key moved fired!");
        }

        @Override
        public void onGeoQueryReady()
        {
            mFirebaseListener.gf_wildcardPoint_onGeoQueryReady();
            Log.i(TAG, "Wildcard: GeoQuery for WildcardPoint ready");
        }

        @Override
        public void onGeoQueryError(DatabaseError error)
        {
            Log.e(TAG, "WildcardPoint: DatabaseError for WildcardPoint fired");
        }
    };

    private GeoQueryEventListener sponsorPrizeListener = new GeoQueryEventListener()
    {
        @Override
        public void onKeyEntered(final String key, GeoLocation location)
        {
            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mFirebaseListener.gf_sponsorPrize_onKeyEntered(key, geoLocation);
        }

        @Override
        public void onKeyExited(String key)
        {
            mFirebaseListener.gf_sponsorPrize_onKeyExited(key, UserData.getInstance(mContext).Is3DCompatibleDevice());
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {

        }

        @Override
        public void onGeoQueryReady()
        {
            mFirebaseListener.gf_sponsorPrize_onGeoQueryReady();
        }

        @Override
        public void onGeoQueryError(DatabaseError error)
        {

        }
    };



    /*
    *******************************************************
    *
    *
    *   ASYNC TASKS
    *
    *******************************************************
    */

    private class InitializeGeolocation extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            //GeoFire
            mGoldPointsRef = new GeoFire(mGoldPoints);
            mSilverPointsRef = new GeoFire(mSilverPoints);
            mBronzePointsRef = new GeoFire(mBronzePoints);
            mWildcardPointsRef = new GeoFire(mWildcardPoints);
            mSponsorPointsRef = new GeoFire(mSponsorPrize);
            return null;
        }
    }

    private class ExecuteGoldPointsQuery extends AsyncTask<Void, Void, Void>
    {
        GeoLocation geoLocation;
        double radius;

        ExecuteGoldPointsQuery(GeoLocation pLocation, double pRadius)
        {
            this.geoLocation = pLocation;
            this.radius = pRadius;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                mGoldPointsQuery = mGoldPointsRef.queryAtLocation(geoLocation, radius);
                mGoldPointsQuery.addGeoQueryEventListener(goldPointsListener);
            }
            catch (IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private class ExecuteSilverPointsQuery extends AsyncTask<Void, Void, Void>
    {
        GeoLocation geoLocation;
        double radius;

        ExecuteSilverPointsQuery(GeoLocation pLocation, double pRadius)
        {
            this.geoLocation = pLocation;
            this.radius = pRadius;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                mSilverPointsQuery = mSilverPointsRef.queryAtLocation(geoLocation, radius);
                mSilverPointsQuery.addGeoQueryEventListener(silverPointsListener);
            }
            catch (IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }

            return null;
        }
    }

    private class ExecuteBronzePointsQuery extends AsyncTask<Void, Void, Void>
    {
        GeoLocation geoLocation;
        double radius;

        ExecuteBronzePointsQuery(GeoLocation pLocation, double pRadius)
        {
            this.geoLocation = pLocation;
            this.radius = pRadius;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                mBronzePointsQuery = mBronzePointsRef.queryAtLocation(geoLocation, radius);
                mBronzePointsQuery.addGeoQueryEventListener(bronzePointsListener);
            }
            catch (IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private class ExecuteWildcardPointsQuery extends AsyncTask<Void, Void, Void>
    {
        GeoLocation geoLocation;
        double radius;

        ExecuteWildcardPointsQuery(GeoLocation pLocation, double pRadius)
        {
            this.geoLocation = pLocation;
            this.radius = pRadius;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                mWildcardPointsQuery = mWildcardPointsRef.queryAtLocation(geoLocation, radius);
                mWildcardPointsQuery.addGeoQueryEventListener(wildcardPointsListener);
            }
            catch (IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }

}
