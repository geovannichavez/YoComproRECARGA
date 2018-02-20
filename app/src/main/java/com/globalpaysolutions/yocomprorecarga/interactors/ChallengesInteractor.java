package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IChallengesInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ChallengesResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public class ChallengesInteractor implements IChallengesInteractor
{
    private static final String TAG = ChallengesInteractor.class.getSimpleName();
    Context mContext;

    //Firebase
    private DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mPlayersPoints = mRootReference.child("locationPlayerRecargo");
    private DatabaseReference mDataPlayersPoints = mRootReference.child("locationPlayerRecargoData");

    private GeoFire mPlayerPntsRef;

    public ChallengesInteractor(Context context)
    {
        this.mContext = context;
        mPlayerPntsRef = new GeoFire(mPlayersPoints);
    }

    @Override
    public void retrieveChallenges(final ChallengesListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<ChallengesResponse> call = apiService.getChallenges(UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

        call.enqueue(new Callback<ChallengesResponse>()
        {
            @Override
            public void onResponse(Call<ChallengesResponse> call, Response<ChallengesResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onRetrieveSuccess(response.body());
                }
                else
                {
                    if(response.code() == 400)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onRetrieveError(response.code(), null, null, errorResponse);
                        }
                        catch (IOException ex)
                        {
                            Log.e(TAG, "Error trying to process Challenges List response");
                        }
                    }
                    else if(response.code() == 426)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onRetrieveError(response.code(), null, errorResponse.getInternalCode(), null);
                        }
                        catch (IOException ex)
                        {
                            Log.e(TAG, "Not a valid client version");
                        }
                    }
                    else
                    {
                        listener.onRetrieveError(response.code(), null, null, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChallengesResponse> call, Throwable t)
            {
                listener.onRetrieveError(0, t, null, null);
            }
        });
    }

    @Override
    public void writePlayerDataLocation(final LatLng location, final ChallengesListener listener)
    {
        try
        {
            if(location != null)
            {
                String playerNick = UserData.getInstance(mContext).getNickname();
                final String playerFacebookID = UserData.getInstance(mContext).getFacebookProfileId();

                Map<String, String> vendorPoint = new HashMap<>();
                vendorPoint.put("Nickname", playerNick);

                mDataPlayersPoints.child(playerFacebookID).setValue(vendorPoint, new DatabaseReference.CompletionListener()
                {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                    {
                        if(databaseError == null)
                        {
                            listener.onPlayerDataInserted(playerFacebookID, location);
                        }
                        else
                        {
                            Log.e(TAG, "Write vendor location on Firebase failed: " + databaseError.getMessage());
                        }
                    }
                });
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteCurrentUserLocation(String key, final ChallengesListener listener)
    {
        try
        {
            mPlayerPntsRef.removeLocation(key, new GeoFire.CompletionListener()
            {
                @Override
                public void onComplete(String key, DatabaseError error)
                {
                    if(error == null)
                    {
                        listener.onPlayerLocationDeleted(key);
                        Log.i(TAG, "Location deleted succesfully for CurrentPlayer " + key);
                    }
                    else
                    {
                        Log.e(TAG, "Error trying to delete location for CurrentPlayer " + key);
                    }

                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Location for current player couldn't be deleted: " + ex.getMessage());
        }
    }

    @Override
    public void setPlayerLocation(String key, GeoLocation geoLocation, final ChallengesListener listener)
    {
        try
        {
            mPlayerPntsRef.setLocation(key, geoLocation, new GeoFire.CompletionListener()
            {
                @Override
                public void onComplete(String key, DatabaseError error)
                {
                    if(error == null)
                    {
                        Log.i(TAG, "Location inserted succesfully for Current Player " + key);
                        listener.onPlayerLocationSet(key);
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

}
