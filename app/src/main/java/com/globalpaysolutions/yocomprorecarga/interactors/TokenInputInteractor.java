package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ITokenInputInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TokenValidationBody;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public class TokenInputInteractor implements ITokenInputInteractor
{
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
        mUserData = new UserData(mContext);
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
        mUserData = new UserData(mContext);
        mUserData.HasConfirmedPhone(pConfirmed);
    }

    @Override
    public void setConfirmedCountry(boolean pConfirmedCountry)
    {
        mUserData = new UserData(mContext);
        mUserData.HasSelectedCountry(pConfirmedCountry);
    }


}
