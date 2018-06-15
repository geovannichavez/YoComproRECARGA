package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IValidatePhoneInteractor;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterPhoneConsumerReqBody;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class ValidatePhoneInteractor implements IValidatePhoneInteractor
{
    private static final String TAG = ValidatePhoneInteractor.class.getSimpleName();

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
                    pListener.onError(codeResponse, null, null, "");
                }
            }
            @Override
            public void onFailure(Call<Countries> call, Throwable t)
            {
                pListener.onError(0, t, null, "");
            }
        });
    }

    @Override
    public void validatePhone(final ValidatePhoneListener pListener, String pPhone, String pCountryID)
    {
        String deviceID = mUserData.getDeviceID();
        RegisterPhoneConsumerReqBody registerConsumerBody = new RegisterPhoneConsumerReqBody();
        registerConsumerBody.setPhone(pPhone);
        registerConsumerBody.setCountryID(pCountryID);
        registerConsumerBody.setDeviceID(deviceID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<RegisterClientResponse> call = apiService.registerConsumer(mUserData.getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, registerConsumerBody);

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
                    try
                    {
                        int codeResponse = response.code();

                        if(codeResponse == 426)
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            pListener.onError(codeResponse, null, errorResponse.getInternalCode(), "");
                        }
                        else
                        {
                            String  rawResponse = response.errorBody().string();
                            Log.i(TAG, rawResponse);
                            pListener.onError(codeResponse, null, null, rawResponse);
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<RegisterClientResponse> call, Throwable t)
            {
                pListener.onError(0, t, null, "");
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

    @Override
    public void authLocalUser(final ValidatePhoneListener listener, String msisdn, String countryID)
    {
        try
        {
            String deviceID = mUserData.getDeviceID();
            RegisterPhoneConsumerReqBody registerConsumerBody = new RegisterPhoneConsumerReqBody();
            registerConsumerBody.setPhone(msisdn);
            registerConsumerBody.setCountryID(countryID);
            registerConsumerBody.setDeviceID(deviceID);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<RegisterClientResponse> call = apiService.registerConsumerLocal(VersionName.getVersionName(mContext, TAG),
                    Constants.PLATFORM, registerConsumerBody);

            call.enqueue(new Callback<RegisterClientResponse>()
            {
                @Override
                public void onResponse(Call<RegisterClientResponse> call, Response<RegisterClientResponse> response)
                {
                    if(response.isSuccessful())
                    {
                        RegisterClientResponse Message = response.body();
                        listener.onAuthLocalSuccess(Message, response.code());
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
                                listener.onError(codeResponse, null, errorResponse.getInternalCode(), "");
                            }
                            else
                            {
                                String  rawResponse = response.errorBody().string();
                                Log.i(TAG, rawResponse);
                                listener.onError(codeResponse, null, null, rawResponse);
                            }
                        }
                        catch (IOException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(Call<RegisterClientResponse> call, Throwable t)
                {
                    listener.onError(0, t, null, "");
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

}
