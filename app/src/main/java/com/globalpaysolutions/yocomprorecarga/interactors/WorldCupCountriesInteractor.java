package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IWorldCupCountriesInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.CountrySelectedReq;
import com.globalpaysolutions.yocomprorecarga.models.api.CountrySelectedResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WorldCupCountriesRspns;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public class WorldCupCountriesInteractor implements IWorldCupCountriesInteractor
{
    private static final String TAG = WorldCupCountriesInteractor.class.getSimpleName();

    private Context mContext;

    public WorldCupCountriesInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveCountries(final WorldCupCountriesListener listener)
    {
        try
        {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<WorldCupCountriesRspns> call = apiService.retrieveWorldcupCountries(
                    UserData.getInstance(mContext).getUserAuthenticationKey(),
                    VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

            call.enqueue(new Callback<WorldCupCountriesRspns>()
            {
                @Override
                public void onResponse(Call<WorldCupCountriesRspns> call, Response<WorldCupCountriesRspns> response)
                {
                    if(response.isSuccessful())
                    {
                        listener.onRetrieveSuccess(response.body());
                    }
                    else
                    {
                        try
                        {
                            if(response.code() == 426 || response.code() == 429 || response.code() == 500 )
                            {
                                Gson gson = new Gson();
                                SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                                listener.onRetrieveError(response.code(), null, errorResponse);
                            }

                        }
                        catch (Exception ex)
                        {
                            Log.e(TAG, "Error: " +ex.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<WorldCupCountriesRspns> call, Throwable t)
                {
                    listener.onRetrieveError(0, t, null);
                }
            });

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void setCountrySelected(int worldCupCountryID, final WorldCupCountriesListener listener)
    {
        try
        {
            CountrySelectedReq selected = new CountrySelectedReq();
            selected.setWorldCupCountryID(worldCupCountryID);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<CountrySelectedResponse> call = apiService.setWorldcupCountry(
                    UserData.getInstance(mContext).getUserAuthenticationKey(),
                    VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, selected);

            call.enqueue(new Callback<CountrySelectedResponse>()
            {
                @Override
                public void onResponse(Call<CountrySelectedResponse> call, Response<CountrySelectedResponse> response)
                {
                    if (response.isSuccessful())
                    {
                        listener.onSelectedSuccess(response.body());
                    }
                    else
                    {
                        try
                        {
                            if(response.code() == 426 || response.code() == 429 || response.code() == 500 )
                            {
                                Gson gson = new Gson();
                                SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                                listener.onSelectedError(response.code(), null, errorResponse);
                            }
                        }
                        catch (Exception ex)
                        {
                            Log.e(TAG, "Error: " +ex.getMessage() );
                        }

                    }
                }

                @Override
                public void onFailure(Call<CountrySelectedResponse> call, Throwable t)
                {
                    listener.onSelectedError(0, t, null);
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void insertUrlMarker(final String urlImgMarker, final WorldCupCountriesListener listener)
    {
        try
        {
            final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference playerData = root.child("locationPlayerRecargoData");
            final String facebookID = UserData.getInstance(mContext).getFacebookProfileId();

            playerData.child(facebookID).addListenerForSingleValueEvent(new ValueEventListener()
            {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot)
               {
                   //Creates map
                   Map<String, Object> playerValues = new HashMap<>();

                   //Checks all properties of the Snapshot
                   for (DataSnapshot snapshot : dataSnapshot.getChildren())
                   {
                       playerValues.put(snapshot.getKey(),snapshot.getValue());
                   }

                   //Puts new properties to child
                   playerValues.put("MarkerUrl", urlImgMarker);

                   //Updates child with new properties and values
                   playerData.child(facebookID).updateChildren(playerValues).addOnCompleteListener(new OnCompleteListener<Void>()
                   {
                       @Override
                       public void onComplete(@NonNull Task<Void> task)
                       {
                           listener.onUpdateMarkerUrl();
                       }
                   });
               }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error updating PlayerData: " + ex.getMessage());
        }
    }

}
