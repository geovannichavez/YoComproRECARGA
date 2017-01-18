package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IValidatePhoneInteractor;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.TokenReqBody;
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
    private UserData userData;

    public ValidatePhoneInteractor(Context pContext)
    {
        this.mContext = pContext;
        userData = new UserData(this.mContext);
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
    public void validatePhone(final ValidatePhoneListener pListener, String pMsisdn, String pCountryID)
    {
        TokenReqBody tokenBody = new TokenReqBody();
        tokenBody.setMsisdn(pMsisdn);
        tokenBody.setCountryId(pCountryID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<SimpleMessageResponse> call = apiService.requestPhoneValidationResult(tokenBody);

        call.enqueue(new Callback<SimpleMessageResponse>()
        {
            @Override
            public void onResponse(Call<SimpleMessageResponse> call, Response<SimpleMessageResponse> response)
            {
                if(response.isSuccessful())
                {
                    SimpleMessageResponse Message = response.body();
                    pListener.onRequestPhoneValResult(Message);
                }
                else
                {
                    int codeResponse = response.code();
                    pListener.onError(codeResponse, null);
                }
            }
            @Override
            public void onFailure(Call<SimpleMessageResponse> call, Throwable t)
            {
                pListener.onError(0, t);
            }
        });
    }

    @Override
    public void saveUserGeneralInfo(String pCountryID, String pIso3Code, String pCountryName, String pPhoneCode)
    {
        userData.SaveUserGeneralInfo(pCountryID, pPhoneCode, pIso3Code, pCountryName);
    }

    @Override
    public void deleteUserGeneralInfo()
    {
        userData.DeleteUserGeneralInfo();
    }


}
