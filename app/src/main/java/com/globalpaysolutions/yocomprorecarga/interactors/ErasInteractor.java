package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.BuildConfig;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IErasInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionReq;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class ErasInteractor implements IErasInteractor
{
    private static final String TAG = ErasInteractor.class.getSimpleName();

    private Context mContext;
    private UserData mUserData;

    public ErasInteractor(Context context)
    {
        this.mContext = context;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void eraSelection(int eraID, final ErasListener listener, final String destiny)
    {
        EraSelectionReq request = new EraSelectionReq();
        request.setAgeID(eraID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<EraSelectionResponse> call = apiService.selectEra(mUserData.getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM, request);

        call.enqueue(new Callback<EraSelectionResponse>()
        {
            @Override
            public void onResponse(Call<EraSelectionResponse> call, Response<EraSelectionResponse> response)
            {
                if (response.isSuccessful())
                {
                    EraSelectionResponse eraSelection = response.body();
                    listener.onEraSelectionSuccess(eraSelection, destiny);
                }
                else
                {
                    int codeResponse = response.code();

                    if(codeResponse == 400)
                    {
                       try
                       {
                           Gson gson = new Gson();
                           SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                           listener.onEraSelectionError(codeResponse, null, errorResponse, null);
                       }
                       catch (IOException ex)
                       {
                           ex.printStackTrace();
                       }
                    }
                    else if(codeResponse == 426)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onEraSelectionError(codeResponse, null, null, errorResponse.getInternalCode());
                        }
                        catch (IOException ex)
                        {
                            ex.printStackTrace();

                        }
                    }
                    else
                    {
                        listener.onEraSelectionError(codeResponse, null, null, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<EraSelectionResponse> call, Throwable t)
            {
                listener.onEraSelectionError(0, t, null, null );
            }
        });
    }

    @Override
    public void retrieveEras(final ErasListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<AgesResponse> call = apiService.retrieveAges(mUserData.getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM);

        call.enqueue(new Callback<AgesResponse>()
        {
            @Override
            public void onResponse(Call<AgesResponse> call, Response<AgesResponse> response)
            {
                if (response.isSuccessful())
                {
                    AgesResponse eras = response.body();
                    listener.onRetrieveSuccess(eras.getAges().getAgesListModel());
                }
                else
                {
                    try
                    {
                        int codeResponse = response.code();
                        if(codeResponse == 426)
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onRetrieveError(codeResponse, null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            listener.onRetrieveError(codeResponse, null, null);
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AgesResponse> call, Throwable t)
            {
                listener.onRetrieveError(0, t, null);
            }
        });
    }

    private String getVersionName()
    {
        String version = "";
        try
        {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            version = pInfo.versionName;//Version Name
            Log.i(TAG, "Version name: " + version);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Could not retrieve version name: " + ex.getMessage());
        }

        return version;
    }
}
