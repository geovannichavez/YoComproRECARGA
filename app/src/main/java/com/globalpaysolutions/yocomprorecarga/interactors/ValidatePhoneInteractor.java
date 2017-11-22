package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IValidatePhoneInteractor;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterPhoneConsumerReqBody;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class ValidatePhoneInteractor implements IValidatePhoneInteractor
{
    private Context mContext;
    private UserData mUserData;

    public ValidatePhoneInteractor(Context pContext)
    {
        this.mContext = pContext;
        mUserData = UserData.getInstance(this.mContext);
    }

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
    public void validatePhone(final ValidatePhoneListener pListener, String pPhone, String pCountryID)
    {
        String deviceID = mUserData.GetDeviceID();
        RegisterPhoneConsumerReqBody registerConsumerBody = new RegisterPhoneConsumerReqBody();
        registerConsumerBody.setPhone(pPhone);
        registerConsumerBody.setCountryID(pCountryID);
        registerConsumerBody.setDeviceID(deviceID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<RegisterClientResponse> call = apiService.registerConsumer(mUserData.getUserAuthenticationKey(), registerConsumerBody);

        call.enqueue(new Callback<RegisterClientResponse>()
        {
            @Override
            public void onResponse(Call<RegisterClientResponse> call, Response<RegisterClientResponse> response)
            {
                if(response.isSuccessful())
                {
                    RegisterClientResponse Message = response.body();
                    pListener.onRequestPhoneValResult(Message, response.code());
                }
                else
                {
                    int codeResponse = response.code();
                    pListener.onError(codeResponse, null);
                }
            }
            @Override
            public void onFailure(Call<RegisterClientResponse> call, Throwable t)
            {
                pListener.onError(0, t);
            }
        });
    }

    @Override
    public void saveUserGeneralInfo(String pCountryID, String pIso3Code, String pCountryName, String pPhoneCode, String pPhone, int pConsumerID)
    {
        mUserData.saveUserPhoneInfo(pCountryID, pPhoneCode, pIso3Code, pCountryName, pPhone, pConsumerID);
    }

    @Override
    public void deleteUserGeneralInfo()
    {
        mUserData.DeleteUserGeneralInfo();
    }


}
