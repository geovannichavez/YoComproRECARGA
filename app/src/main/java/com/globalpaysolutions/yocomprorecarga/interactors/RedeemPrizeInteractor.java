package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IRedeemPrizeInteractor;
import com.globalpaysolutions.yocomprorecarga.models.OperatorsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ActivatePrizeReq;
import com.globalpaysolutions.yocomprorecarga.models.api.ActivatePrizeResponse;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 22/11/2017.
 */

public class RedeemPrizeInteractor implements IRedeemPrizeInteractor
{
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
        final Call<ActivatePrizeResponse> call = apiService.activatePrize(UserData.getInstance(mContext).getUserAuthenticationKey(), requestBody);

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
                    int codeResponse = response.code();
                    listener.onError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<ActivatePrizeResponse> call, Throwable t)
            {
                listener.onError(9, t);
            }
        });

    }
}
