package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ISouvenirsInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvenirsResponse;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public class SouvenirsInteractor implements ISouvenirsInteractor
{
    private static final String TAG = SouvenirsInteractor.class.getSimpleName();

    private Context mContext;
    private UserData mUserData;

    public SouvenirsInteractor(Context context)
    {
        this.mContext = context;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void requestUserSouvenirs(final SouvenirsListeners listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<SouvenirsResponse> call = apiService.getSouvenirs(mUserData.getUserAuthenticationKey());

        call.enqueue(new Callback<SouvenirsResponse>()
        {
            @Override
            public void onResponse(Call<SouvenirsResponse> call, Response<SouvenirsResponse> response)
            {
                if(response.isSuccessful())
                {

                    SouvenirsResponse souvenirs = response.body();
                    listener.onSuccess(souvenirs.getListSouvenirsByConsumer());
                }
                else
                {
                    int codeResponse = response.code();
                    listener.onError(codeResponse, null);
                }
            }
            @Override
            public void onFailure(Call<SouvenirsResponse> call, Throwable t)
            {
                listener.onError(0, t);
            }
        });
    }
}
