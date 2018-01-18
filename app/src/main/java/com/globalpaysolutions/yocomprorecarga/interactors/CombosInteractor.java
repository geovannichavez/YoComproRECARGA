package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.support.coreui.BuildConfig;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ICombosInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.CombosResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeComboReq;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public class CombosInteractor implements ICombosInteractor
{
    private static final String TAG = CombosInteractor.class.getSimpleName();

    private Context mContext;

    public CombosInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveCombos(final CombosListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<CombosResponse> call = apiService.getCombos(UserData.getInstance(mContext).getUserAuthenticationKey(),
                BuildConfig.VERSION_NAME, Constants.PLATFORM);

        call.enqueue(new Callback<CombosResponse>()
        {
            @Override
            public void onResponse(Call<CombosResponse> call, Response<CombosResponse> response)
            {
                if(response.isSuccessful())
                {
                    CombosResponse souvenirs = response.body();
                    listener.onRetrieveSuccess(souvenirs);
                }
                else
                {
                    try
                    {
                        if(response.code() == 426)
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onRetrieveError(response.code(), null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            listener.onRetrieveError(response.code(), null, null);
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<CombosResponse> call, Throwable t)
            {
                listener.onRetrieveError(0, t, null);
            }
        });
    }

    @Override
    public void exchangeCombo(final CombosListener listener, int comboID)
    {
        ExchangeComboReq exchangeCombo = new ExchangeComboReq();
        exchangeCombo.setComboID(comboID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<WinPrizeResponse> call = apiService.exchangeCombo(UserData.getInstance(mContext).getUserAuthenticationKey(),
                BuildConfig.VERSION_NAME, Constants.PLATFORM, exchangeCombo);

        call.enqueue(new Callback<WinPrizeResponse>()
        {
            @Override
            public void onResponse(Call<WinPrizeResponse> call, Response<WinPrizeResponse> response)
            {
                if(response.isSuccessful())
                {
                    try
                    {
                        WinPrizeResponse redeemPrize = response.body();
                        if(response.code() == 200)
                        {
                            listener.onExchangeSouvSuccess(redeemPrize);
                        }
                        else if (response.code() == 202)
                        {
                            listener.onAcceptedResponse();
                        }
                    }
                    catch (Exception ex)
                    {
                        Log.e(TAG, ex.getLocalizedMessage());
                    }
                }
                else
                {
                    try
                    {
                        int codeResponse = response.code();
                        Gson gson = new Gson();
                        SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                        listener.onExchangeComboError(errorResponse, codeResponse, null);
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
                listener.onExchangeComboError(null, 0, t);
            }
        });

    }
}
