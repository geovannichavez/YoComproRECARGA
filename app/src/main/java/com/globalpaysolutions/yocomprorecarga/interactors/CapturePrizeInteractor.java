package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ICapturePrizeInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TrackingResponse;
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
        this.mUserData = new UserData(mContext);
    }

    @Override
    public void retrieveConsumerTracking()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<TrackingResponse> call = apiService.getConsumerTracking(mUserData.GetConsumerID());

        call.enqueue(new Callback<TrackingResponse>()
        {
            @Override
            public void onResponse(Call<TrackingResponse> call, Response<TrackingResponse> response)
            {
                if(response.isSuccessful())
                {
                    TrackingResponse trackingResponse = response.body();
                    mListener.onRetrieveTracking(trackingResponse);
                }
                else
                {
                    int codeResponse = response.code();
                    mListener.onTrackingError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<TrackingResponse> call, Throwable t)
            {
                mListener.onTrackingError(0, t);
            }
        });

    }

    @Override
    public void exchangePrizeData(LatLng pLocation, String pFirebaseID, int pConsumerID)
    {
        ExchangeReqBody requestBody = new ExchangeReqBody();
        requestBody.setConsumerID(pConsumerID);
        requestBody.setLocationID(pFirebaseID);
        requestBody.setLatitude(pLocation.latitude);
        requestBody.setLongitude(pLocation.longitude);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<ExchangeResponse> call = apiService.exchangeCoin(requestBody);

        call.enqueue(new Callback<ExchangeResponse>()
        {
            @Override
            public void onResponse(Call<ExchangeResponse> call, Response<ExchangeResponse> response)
            {
                if(response.isSuccessful())
                {
                    ExchangeResponse exchangeResponse = response.body();
                    mListener.onExchangeCoinSuccess(exchangeResponse);
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
    public void saveUserTracking(TrackingResponse pTracking)
    {
        mUserData.SaveUserTrackingProgess(pTracking.getTotalWinCoins(), pTracking.getTotalWinPrizes(), pTracking.getCurrentCoinsProgress());
    }
}
