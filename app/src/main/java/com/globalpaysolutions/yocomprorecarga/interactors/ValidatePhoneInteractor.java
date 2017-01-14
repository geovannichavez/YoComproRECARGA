package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.models.Countries;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class ValidatePhoneInteractor implements IValidatePhoneInteractor
{
    @Override
    public void fethCountries(final ValidatePhoneListener pListener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<Countries> call = apiService.getCountries();


        call.enqueue(new Callback<Countries>()
        {
            @Override
            public void onResponse(Call<Countries> call, Response<Countries> response)
            {
                if(response.isSuccessful())
                {

                    Countries countries = response.body();
                    pListener.onGetCountriesSuccess(countries);
                }
                else
                {
                    int codeResponse = response.code();
                    pListener.onError(codeResponse, null);
                }
            }
            @Override
            public void onFailure(Call<Countries> call, Throwable t)
            {
                pListener.onError(0, t);
            }
        });
    }

    @Override
    public void validatePhone(ValidatePhoneListener pListener, String pPhoneNumber)
    {

    }

}
