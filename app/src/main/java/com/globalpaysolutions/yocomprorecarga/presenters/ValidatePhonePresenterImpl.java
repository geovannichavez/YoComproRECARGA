package com.globalpaysolutions.yocomprorecarga.presenters;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.views.ValidatePhoneView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class ValidatePhonePresenterImpl implements IValidatePhonePresenter
{
    private ValidatePhoneView View;
    private Countries countries;

    public  ValidatePhonePresenterImpl(ValidatePhoneView pView, AppCompatActivity pActivity, Context pContext)
    {
        this.View = pView;
    }

    @Override
    public void fetchCountries()
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
                    countries = response.body();
                    View.renderCountries(countries);
                }
                else
                {

                }
            }
            @Override
            public void onFailure(Call<Countries> call, Throwable t)
            {

            }
        });

    }
}
