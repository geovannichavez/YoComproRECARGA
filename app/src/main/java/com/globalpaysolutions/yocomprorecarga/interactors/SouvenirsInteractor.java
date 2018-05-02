package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ISouvenirsInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeSouvenirReq;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvenirsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvsProgressResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public class SouvenirsInteractor implements ISouvenirsInteractor
{
    private static final String TAG = SouvenirsInteractor.class.getSimpleName();

    private Context mContext;
    private UserData mUserData;

    public SouvenirsInteractor(Context context)
    {
        this.mContext = context;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void requestUserSouvenirs(final SouvenirsListeners listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<JsonObject> call = apiService.getGropuedSouvenirs(mUserData.getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

        call.enqueue(new Callback<JsonObject>()
        {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(response.isSuccessful())
                {
                    try
                    {
                        listener.onSuccess(response.body());
                    }
                    catch (Exception ex)
                    {
                        Log.e(TAG, "Error processing raw response: " + ex.getMessage());
                    }
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
                            listener.onError(codeResponse, null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            listener.onError(codeResponse, null, null);
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                listener.onError(0, t, null);
            }
        });
    }

    @Override
    public void atemptExchangeSouv(final SouvenirsListeners listener, int souvenirID)
    {
        ExchangeSouvenirReq request = new ExchangeSouvenirReq();
        request.setSouvenirID(souvenirID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<WinPrizeResponse> call = apiService.exchangeSouvenir(mUserData.getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, request);

        call.enqueue(new Callback<WinPrizeResponse>()
        {
            @Override
            public void onResponse(Call<WinPrizeResponse> call, Response<WinPrizeResponse> response)
            {
                if(response.isSuccessful())
                {
                    WinPrizeResponse redeemPrize = response.body();
                    listener.onExchangeSouvSuccess(redeemPrize);
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
                            listener.onExchangeSouvError(codeResponse, null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            listener.onExchangeSouvError(codeResponse, null, null);
                            Log.e(TAG, response.errorBody().toString());
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WinPrizeResponse> call, Throwable t)
            {
                listener.onExchangeSouvError(0, t, null);
            }
        });
    }

    @Override
    public void requestSouvsProgress(final SouvenirsListeners listener)
    {
        try
        {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<SouvsProgressResponse> call = apiService.retrieveSouvsProgress(mUserData.getUserAuthenticationKey(),
                    VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

            call.enqueue(new Callback<SouvsProgressResponse>()
            {
                @Override
                public void onResponse(Call<SouvsProgressResponse> call, Response<SouvsProgressResponse> response)
                {
                    if(response.isSuccessful())
                    {
                        listener.onGetProgressSuccess(response.body());
                    }
                    else
                    {
                        try
                        {
                            int codeResponse = response.code();
                            if(codeResponse == 426 || codeResponse == 429 || codeResponse == 500)
                            {
                                try
                                {
                                    Gson gson = new Gson();
                                    SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                                    listener.onGetProgressError(codeResponse, null, errorResponse);
                                }
                                catch (IOException ex)
                                {
                                    listener.onGetProgressError(codeResponse, null, null);
                                }
                            }
                            else
                            {
                                listener.onGetProgressError(codeResponse, null, null);
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SouvsProgressResponse> call, Throwable t)
                {
                    listener.onGetProgressError(0, t, null);
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on request: " + ex.getMessage());
        }
    }


}
