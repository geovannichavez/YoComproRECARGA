package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.BuildConfig;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IRedeemPrizeInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ActivatePrizeReq;
import com.globalpaysolutions.yocomprorecarga.models.api.ActivatePrizeResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 22/11/2017.
 */

public class RedeemPrizeInteractor implements IRedeemPrizeInteractor
{
    private static final String TAG = RedeemPrizeInteractor.class.getSimpleName();
    private Context mContext;

    public RedeemPrizeInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void atemptRedeemPrize(String pinCode, String phone, final RedeemPrizeListener listener)
    {
        final ActivatePrizeReq requestBody = new ActivatePrizeReq();
        requestBody.setPhone(phone);
        requestBody.setPIN(pinCode);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<ActivatePrizeResponse> call = apiService.activatePrize(UserData.getInstance(mContext).getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM, requestBody);

        call.enqueue(new Callback<ActivatePrizeResponse>()
        {
            @Override
            public void onResponse(Call<ActivatePrizeResponse> call, Response<ActivatePrizeResponse> response)
            {

                if(response.isSuccessful())
                {

                    ActivatePrizeResponse activationResult = response.body();
                    listener.onSuccess(activationResult);
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
            public void onFailure(Call<ActivatePrizeResponse> call, Throwable t)
            {
                listener.onError(9, t, null);
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
