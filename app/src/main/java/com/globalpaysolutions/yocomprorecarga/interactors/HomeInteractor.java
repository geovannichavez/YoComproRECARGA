package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IHomeInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PendingsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.StoreAirtimeReportReqBody;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.PlayerPointData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.SalePointData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.VendorPointData;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private DatabaseReference mPlayersPoints = mRootReference.child("locationPlayerRecargo");
    private DatabaseReference mDataPlayersPoints = mRootReference.child("locationPlayerRecargoData");

    //GeoFire
    private GeoFire mSalesPntsRef;
    private GeoFire mVendorPntsRef;
    private GeoFire mPlayerPntsRef;

    //GeoFire Queries
    private GeoQuery mSalesPntsQuery;
    private GeoQuery mVendorPntsQuery;
    private GeoQuery mPlayerPntsQuery;

    public HomeInteractor(Context pContext, HomeListener pListener)
    {
        mContext = pContext;
        mHomeListener = pListener;
    }

    public HomeInteractor(Context context)
    {
        mContext = context;
    }

    @Override
    public void initializeGeolocation()
    {
        //GeoFire
        mSalesPntsRef = new GeoFire(mSalesPoints);
        mVendorPntsRef = new GeoFire(mVendorPoints);
        mPlayerPntsRef = new GeoFire(mPlayersPoints);
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
    public void playersPointsQuery(GeoLocation location)
    {
        try
        {
            mPlayerPntsQuery = mPlayerPntsRef.queryAtLocation(location, Constants.PLAYER_RADIUS_KM);
            mPlayerPntsQuery.addGeoQueryEventListener(playersPointsListener);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void playersPointsUpdateCriteria(GeoLocation location)
    {
        try
        {
            mPlayerPntsQuery.setLocation(location, Constants.PLAYER_RADIUS_KM);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertCurrentPlayerData(final GeoLocation location, String facebookID)
    {
        try
        {
            String playerNick = UserData.getInstance(mContext).getNickname();
            final String playerFacebookID = UserData.getInstance(mContext).getFacebookProfileId();
            final String urlImgMarker = UserData.getInstance(mContext).getWorldcupMarkerUrl();

            Map<String, String> vendorPoint = new HashMap<>();
            vendorPoint.put("Nickname", playerNick);
            vendorPoint.put("MarkerUrl", urlImgMarker);

            mDataPlayersPoints.child(playerFacebookID).setValue(vendorPoint, new DatabaseReference.CompletionListener()
            {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                {
                    if(databaseError == null)
                    {
                        //On data insert success, inserts location
                        mHomeListener.fb_currentPlayerDataInserted(playerFacebookID, location);
                    }
                    else
                    {
                        Log.e(TAG, "Write vendor location on Firebase failed: " + databaseError.getMessage());
                    }
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setPlayerLocation(String key, GeoLocation location)
    {
        try
        {
            mPlayerPntsRef.setLocation(key, location, new GeoFire.CompletionListener()
            {
                @Override
                public void onComplete(String key, DatabaseError error)
                {
                    if(error == null)
                    {
                        Log.i(TAG, "Location inserted succesfully for Current Player " + key);
                    }
                    else
                    {
                        Log.e(TAG, "Error trying to insert location for Current Player " + key);
                    }

                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Location for current player could not be inserted: " + ex.getMessage());
        }
    }

    @Override
    public void deletePlayerLocation(String key)
    {
        try
        {
            mPlayerPntsRef.removeLocation(key, new GeoFire.CompletionListener()
            {
                @Override
                public void onComplete(String key, DatabaseError error)
                {
                    if(error == null)
                        Log.i(TAG, "Location deleted succesfully for CurrentPlayer " + key);
                    else
                        Log.e(TAG, "Error trying to delete location for CurrentPlayer " + key);
                }
            });


            /*mDataVendorPoints.child(key).removeValue(new DatabaseReference.CompletionListener()
            {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                {
                    Log.d(TAG, "Se borró current palyer data de DataVendor, ref: " + databaseReference.getKey());
                }
            });*/
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Location for current player couldn't be deleted: " + ex.getMessage());
        }
    }

    @Override
    public void sendStoreAirtimeReport(String pStoreName, String pAddressStore, double pLongitude, double pLatitude, String pFirebaseID)
    {
        UserData userData = UserData.getInstance(mContext);
        StoreAirtimeReportReqBody airtimeReportReqBody = new StoreAirtimeReportReqBody();
        airtimeReportReqBody.setStoreName(pStoreName);
        airtimeReportReqBody.setAddressStore(pAddressStore);
        airtimeReportReqBody.setLongitude(pLongitude);
        airtimeReportReqBody.setLatitude(pLatitude);
        airtimeReportReqBody.setFirebaseID(pFirebaseID);
        airtimeReportReqBody.setConsumerID(userData.GetConsumerID());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<SimpleMessageResponse> call = apiService.sendStoreAirtimeReport(UserData.getInstance(mContext).getUserAuthenticationKey(), airtimeReportReqBody);

        call.enqueue(new Callback<SimpleMessageResponse>()
        {
            @Override
            public void onResponse(Call<SimpleMessageResponse> call, Response<SimpleMessageResponse> response)
            {
                if(response.isSuccessful())
                {
                    SimpleMessageResponse Message = response.body();
                    mHomeListener.onStoreAirtimeReportSuccess(Message);
                }
                else
                {
                    int codeResponse = response.code();
                    mHomeListener.onError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<SimpleMessageResponse> call, Throwable t)
            {
                mHomeListener.onError(0, t);
            }
        });
    }

    @Override
    public void getPendingChallenges(final HomeListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<PendingsResponse> call = apiService.getPendingChallenges(UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

        call.enqueue(new Callback<PendingsResponse>()
        {
            @Override
            public void onResponse(Call<PendingsResponse> call, Response<PendingsResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onPendingChallengesSuccess(response.body());
                }
                else
                {
                    listener.onPendingChallengesError(response.code(), null);
                }
            }

            @Override
            public void onFailure(Call<PendingsResponse> call, Throwable t)
            {
                listener.onPendingChallengesError(0, t);
            }
        });
    }

    @Override
    public void downloadMarkerBmp(String markerUrl, String markerName, HomeListener listener)
    {
        mHomeListener = listener;
        try
        {
            new FetchMarker(listener, markerName).execute(markerUrl).get();
        }
        catch (InterruptedException e)
        {
            Log.e(TAG, "Error: " + e.getMessage());
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    public static class FetchMarker extends AsyncTask<String, Void, Bitmap>
    {
        Bitmap mBitmap;
        HomeListener mListener;
        String mName;

        private FetchMarker(HomeListener listener, String markerName)
        {
            mListener = listener;
            mName = markerName;
        }

        @Override
        protected Bitmap doInBackground(String... strings)
        {
            try
            {
                URL bitmapUrl = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) bitmapUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                mBitmap = Bitmap.createScaledBitmap(bitmap , bitmap.getWidth()/2, bitmap.getHeight()/2, false);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                mBitmap = null;
            }
            return mBitmap;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            mListener.onRetrieveBitmapSuccess(bitmap, mName);
        }

    }




    /*
    *
    * LISTENERS
    *
    */

    //Static points
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
                    if(salePoint != null)
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

    //Vendor points
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
                    if(vendorPoint != null)
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

    //Player points
    private GeoQueryEventListener playersPointsListener = new GeoQueryEventListener()
    {
        @Override
        public void onKeyEntered(final String key, final GeoLocation location)
        {
            //Reads values and data for key entered
            mDataPlayersPoints.child(key).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    PlayerPointData player = dataSnapshot.getValue(PlayerPointData.class);
                    if(player != null)
                    {
                        LatLng geoLocation = new LatLng(location.latitude, location.longitude);
                        mHomeListener.gf_playerPoint_onKeyEntered(key, geoLocation, player);
                        mHomeListener.fb_playerPoint_onDataChange(key, player);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    mHomeListener.fb_playerPoint_onCancelled(databaseError);
                }
            });
        }

        @Override
        public void onKeyExited(String key)
        {
            mHomeListener.gf_playerPoint_onKeyExited(key);
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location)
        {
            LatLng geoLocation = new LatLng(location.latitude, location.longitude);
            mHomeListener.gf_playerPoint_onKeyMoved(key, geoLocation);
        }

        @Override
        public void onGeoQueryReady()
        {
            mHomeListener.gf_playerPoint_onGeoQueryReady();
        }

        @Override
        public void onGeoQueryError(DatabaseError error)
        {
            mHomeListener.gf_playerPoint_onGeoQueryError(error);
        }
    };


}
