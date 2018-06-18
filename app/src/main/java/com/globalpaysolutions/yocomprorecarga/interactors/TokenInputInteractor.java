package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ITokenInputInteractor;
import com.globalpaysolutions.yocomprorecarga.models.Country;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TokenValidationBody;
import com.globalpaysolutions.yocomprorecarga.models.api.TokenValidationLocalReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.ValidateLocalSmsResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public class TokenInputInteractor implements ITokenInputInteractor
{
    private static final String TAG = TokenInputInteractor.class.getSimpleName();
    private Context mContext;
    private UserData mUserData;
    private TokenInputListener mListener;

    public TokenInputInteractor(Context pContext)
    {
        mContext = pContext;
    }

    @Override
    public void sendTokenValidation(final TokenInputListener pListener, String pToken)
    {
        mUserData = UserData.getInstance(mContext);
        int consumerID = mUserData.GetConsumerID();

        TokenValidationBody tokenValidation = new TokenValidationBody();
        tokenValidation.setToken(pToken);
        tokenValidation.setConsumerID(consumerID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<SimpleMessageResponse> call = apiService.requestTokenValidation(mUserData.getUserAuthenticationKey(), tokenValidation);

        call.enqueue(new Callback<SimpleMessageResponse>()
        {
            @Override
            public void onResponse(Call<SimpleMessageResponse> call, Response<SimpleMessageResponse> response)
            {
                if(response.isSuccessful())
                {
                    SimpleMessageResponse Message = response.body();
                    pListener.onValidationTokenResult(Message);
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
    public void setConfirmedPhone(boolean pConfirmed)
    {
        mUserData = UserData.getInstance(mContext);
        mUserData.HasConfirmedPhone(pConfirmed);
    }

    @Override
    public void setConfirmedCountry(boolean pConfirmedCountry)
    {
        mUserData = UserData.getInstance(mContext);
        mUserData.HasSelectedCountry(pConfirmedCountry);
    }

    @Override
    public void validateSmsTokenLocalAuth(final TokenInputListener listener, String token)
    {
        try
        {
            Country country = UserData.getInstance(mContext).getSelectedCountry();
            String deviceID = UserData.getInstance(mContext).getDeviceID();
            String phone = country.getPhoneCode().concat(UserData.getInstance(mContext).getUserPhone());

            TokenValidationLocalReqBody tokenValidation = new TokenValidationLocalReqBody();
            tokenValidation.setToken(token);
            tokenValidation.setCountryID(country.getCountrycode());
            tokenValidation.setDeviceID(deviceID);
            tokenValidation.setPhone(phone);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<ValidateLocalSmsResponse> call = apiService.requestTokenValidationLocal(tokenValidation,
                    VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

            call.enqueue(new Callback<ValidateLocalSmsResponse>()
            {
                @Override
                public void onResponse(Call<ValidateLocalSmsResponse> call, Response<ValidateLocalSmsResponse> response)
                {
                    if(response.isSuccessful())
                    {
                        listener.onValidationTokenLocalResult(response.body());
                    }
                    else
                    {
                        int codeResponse = response.code();
                        listener.onError(codeResponse, null);

                    }
                }
                @Override
                public void onFailure(Call<ValidateLocalSmsResponse> call, Throwable t)
                {
                    listener.onError(0, t);
                }
            });

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on validateSmsTokenLocalAuth: " + ex.getMessage());
        }
    }


}
