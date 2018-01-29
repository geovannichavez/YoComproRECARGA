package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.BuildConfig;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ISouvenirsInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeSouvenirReq;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvenirsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.gson.Gson;

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
        final Call<SouvenirsResponse> call = apiService.getSouvenirs(mUserData.getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM);

        call.enqueue(new Callback<SouvenirsResponse>()
        {
            @Override
            public void onResponse(Call<SouvenirsResponse> call, Response<SouvenirsResponse> response)
            {
                if(response.isSuccessful())
                {
                    SouvenirsResponse souvenirs = response.body();
                    listener.onSuccess(souvenirs.getListSouvenirsByConsumer());
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
            public void onFailure(Call<SouvenirsResponse> call, Throwable t)
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
                getVersionName(), Constants.PLATFORM, request);

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
