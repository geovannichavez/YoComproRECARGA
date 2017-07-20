package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ICapturePrizeInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.android.gms.maps.model.LatLng;

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
        final Call<Tracking> call = apiService.getConsumerTracking(mUserData.getUserAuthenticationKey());

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
                    int codeResponse = response.code();
                    mListener.onTrackingError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<Tracking> call, Throwable t)
            {
                mListener.onTrackingError(0, t);
            }
        });

    }

    @Override
    public void exchangePrizeData(LatLng pLocation, String pFirebaseID, int pChestType)
    {
        ExchangeReqBody requestBody = new ExchangeReqBody();
        requestBody.setLocationID(pFirebaseID);
        requestBody.setLatitude(pLocation.latitude);
        requestBody.setLongitude(pLocation.longitude);
        requestBody.setChestType(pChestType);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<ExchangeResponse> call = apiService.exchangeChest(mUserData.getUserAuthenticationKey(), requestBody);

        call.enqueue(new Callback<ExchangeResponse>()
        {
            @Override
            public void onResponse(Call<ExchangeResponse> call, Response<ExchangeResponse> response)
            {
                if(response.isSuccessful())
                {
                    ExchangeResponse exchangeResponse = response.body();
                    mListener.onExchangeChestSuccess(exchangeResponse);
                }
                else
                {
                    int codeResponse = response.code();
                    mListener.onExchangeError(codeResponse, null);
                }
            }
            @Override
            public void onFailure(Call<ExchangeResponse> call, Throwable t)
            {
                mListener.onExchangeError(0, t);
            }
        });

    }

    @Override
    public void saveUserTracking(Tracking pTracking)
    {
        mUserData.SaveUserTrackingProgess(pTracking.getTotalWinCoins(), pTracking.getTotalWinPrizes(), pTracking.getCurrentCoinsProgress());
    }

    @Override
    public void atemptRedeemPrize()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<WinPrizeResponse> call = apiService.redeemPrize(mUserData.getUserAuthenticationKey());

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
                    int codeResponse = response.code();
                    mListener.onRedeemPrizeError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<WinPrizeResponse> call, Throwable t)
            {
                mListener.onRedeemPrizeError(0, t);
            }
        });

    }
}
