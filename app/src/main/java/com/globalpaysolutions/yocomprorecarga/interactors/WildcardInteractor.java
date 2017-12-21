package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IWildcardInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeWildcardReq;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeWildcardResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 18/11/2017.
 */

public class WildcardInteractor implements IWildcardInteractor
{
    private static final String TAG = WildcardInteractor.class.getSimpleName();
    private Context mContext;

    public WildcardInteractor(Context context)
    {
        mContext = context;
    }

    @Override
    public void exchangeWildcard(String pFirebaseID, int eraID, final WildcardListener listener)
    {
        ExchangeWildcardReq requestBody = new ExchangeWildcardReq();
        //requestBody.setAgeID(1); //TODO
        requestBody.setAgeID(eraID);
        requestBody.setLocationID(pFirebaseID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<ExchangeWildcardResponse> call = apiService.exchangeWildcard(UserData.getInstance(mContext).getUserAuthenticationKey(), requestBody);

        call.enqueue(new Callback<ExchangeWildcardResponse>()
        {
            @Override
            public void onResponse(Call<ExchangeWildcardResponse> call, Response<ExchangeWildcardResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onExchangeWildcardSuccess(response.body());
                }
                else
                {
                    int codeResponse = response.code();
                    listener.onExchangeWildcardError(codeResponse, null);
                    Log.e(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ExchangeWildcardResponse> call, Throwable t)
            {
                listener.onExchangeWildcardError(0, t);
            }
        });
    }

    @Override
    public void saveUserTracking(Tracking tracking)
    {
        try
        {
            UserData.getInstance(mContext).SaveUserTrackingProgess(
                    tracking.getTotalWinCoins(),
                    tracking.getTotalWinPrizes(),
                    tracking.getCurrentCoinsProgress(),
                    tracking.getTotalSouvenirs(),
                    tracking.getAgeID());
        }
        catch (Exception ex)
        {
            Crashlytics.logException(ex);
        }
    }

}
