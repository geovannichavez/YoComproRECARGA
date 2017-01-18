package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IRequestTopup;
import com.globalpaysolutions.yocomprorecarga.models.OperatorsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public class RequestTopupInteractor implements IRequestTopup
{
    int countryID = 69;

    @Override
    public void fetchOperators(final RequestTopupListener pListener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<OperatorsResponse> call = apiService.getOperators(countryID);


        call.enqueue(new Callback<OperatorsResponse>()
        {
            @Override
            public void onResponse(Call<OperatorsResponse> call, Response<OperatorsResponse> response)
            {
                if(response.isSuccessful())
                {

                    OperatorsResponse operators = response.body();
                    pListener.onGetOperatorsSuccess(operators.getOperators().getCountryOperators());
                }
                else
                {
                    int codeResponse = response.code();
                    pListener.onError(codeResponse, null);
                }
            }
            @Override
            public void onFailure(Call<OperatorsResponse> call, Throwable t)
            {
                pListener.onError(0, t);
            }
        });
    }
}
