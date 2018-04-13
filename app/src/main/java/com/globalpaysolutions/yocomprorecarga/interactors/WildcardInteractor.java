package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.globalpaysolutions.yocomprorecarga.BuildConfig;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IWildcardInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeWildcardReq;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeWildcardResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.gson.Gson;

import java.io.IOException;

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
        requestBody.setAgeID(eraID);
        requestBody.setLocationID(pFirebaseID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<ExchangeWildcardResponse> call = apiService.exchangeWildcard(UserData.getInstance(mContext).getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM,
                requestBody);

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
                    try
                    {
                        int codeResponse = response.code();

                        if(codeResponse == 426)
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onExchangeWildcardError(codeResponse, null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            listener.onExchangeWildcardError(codeResponse, null, null);
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ExchangeWildcardResponse> call, Throwable t)
            {
                listener.onExchangeWildcardError(0, t, null);
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
            UserData.getInstance(mContext).saveWorldcupTracking(tracking.getCountryID(),
                    tracking.getCountryName(),
                    tracking.getUrlImg(),
                    tracking.getUrlImgMarker());
        }
        catch (Exception ex)
        {
            Crashlytics.logException(ex);
        }
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
