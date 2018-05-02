package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.globalpaysolutions.yocomprorecarga.BuildConfig;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ICapturePrizeInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public class CapturePrizeInteractor implements ICapturePrizeInteractor
{
    private static final String TAG = CapturePrizeInteractor.class.getSimpleName();
    private Context mContext;
    private CapturePrizeListener mListener;
    private UserData mUserData;

    public CapturePrizeInteractor(Context pContext, CapturePrizeListener pListener)
    {
        this.mContext = pContext;
        this.mListener = pListener;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void retrieveConsumerTracking()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<Tracking> call = apiService.getConsumerTracking(mUserData.getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM);

        call.enqueue(new Callback<Tracking>()
        {
            @Override
            public void onResponse(Call<Tracking> call, Response<Tracking> response)
            {
                if(response.isSuccessful())
                {
                    Tracking trackingResponse = response.body();
                    mListener.onRetrieveTracking(trackingResponse);
                    Log.i(TAG, "TotalWinCoins:" + String.valueOf(trackingResponse.getTotalWinCoins())
                            + ", TotalWinPrizes:" + String.valueOf(trackingResponse.getTotalWinCoins())
                            + ", CurrentCoinsProgress:" + String.valueOf(trackingResponse.getCurrentCoinsProgress()));
                }
                else
                {
                    try
                    {
                        if(response.code() == 426)
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            mListener.onTrackingError(response.code(), null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            mListener.onTrackingError(response.code(), null, null);
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Tracking> call, Throwable t)
            {
                mListener.onTrackingError(0, t, null);
            }
        });

    }

    @Override
    public void openCoinsChest(LatLng pLocation, final String pFirebaseID, final int pChestType, int pEraID)
    {
        ExchangeReqBody requestBody = new ExchangeReqBody();
        requestBody.setLocationID(pFirebaseID);
        requestBody.setLatitude(pLocation.latitude);
        requestBody.setLongitude(pLocation.longitude);
        requestBody.setChestType(pChestType);
        requestBody.setAgeID(1);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<ExchangeResponse> call = apiService.exchangeChest(mUserData.getUserAuthenticationKey(), requestBody,
                getVersionName(), Constants.PLATFORM);

        call.enqueue(new Callback<ExchangeResponse>()
        {
            @Override
            public void onResponse(Call<ExchangeResponse> call, Response<ExchangeResponse> response)
            {
                if(response.isSuccessful())
                {
                    ExchangeResponse exchangeResponse = response.body();
                    mListener.onOpenChestSuccess(exchangeResponse, pChestType, pFirebaseID);
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
                           mListener.onOpenChestError(codeResponse, null, errorResponse.getInternalCode());
                       }
                       else
                       {
                           mListener.onOpenChestError(codeResponse, null, null);
                       }

                   }
                   catch (Exception ex)
                   {
                       ex.printStackTrace();
                   }
                }
            }
            @Override
            public void onFailure(Call<ExchangeResponse> call, Throwable t)
            {
                mListener.onOpenChestError(0, t, null);
            }
        });

    }

    @Override
    public void saveUserTracking(Tracking pTracking)
    {
        try
        {
            mUserData.SaveUserTrackingProgess(pTracking.getTotalWinCoins(),
                    pTracking.getTotalWinPrizes(),
                    pTracking.getCurrentCoinsProgress(),
                    pTracking.getTotalSouvenirs(),
                    pTracking.getAgeID());
            mUserData.saveWorldcupTracking(pTracking.getCountryID(),
                    pTracking.getCountryName(),
                    pTracking.getUrlImg(),
                    pTracking.getUrlImgMarker());
        }
        catch (Exception ex)
        {
            Crashlytics.logException(ex);
        }
    }

    @Override
    public void atemptRedeemPrize()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<WinPrizeResponse> call = apiService.redeemPrize(mUserData.getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM);

        call.enqueue(new Callback<WinPrizeResponse>()
        {
            @Override
            public void onResponse(Call<WinPrizeResponse> call, Response<WinPrizeResponse> response)
            {
                if(response.isSuccessful())
                {
                    WinPrizeResponse redeemPrize = response.body();
                    mListener.onRedeemPrizeSuccess(redeemPrize);
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
                            mListener.onRedeemPrizeError(codeResponse, null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            mListener.onRedeemPrizeError(codeResponse, null, null);
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
                mListener.onRedeemPrizeError(0, t, null);
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
