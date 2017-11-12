package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IErasInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesResponse;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class ErasInteractor implements IErasInteractor
{
    private static final String TAG = ErasInteractor.class.getSimpleName();

    private Context mContext;
    private UserData mUserData;

    public ErasInteractor(Context context)
    {
        this.mContext = context;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void retrieveEras(final ErasListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<AgesResponse> call = apiService.retrieveAges(mUserData.getUserAuthenticationKey());

        call.enqueue(new Callback<AgesResponse>()
        {
            @Override
            public void onResponse(Call<AgesResponse> call, Response<AgesResponse> response)
            {
                if (response.isSuccessful())
                {
                    AgesResponse eras = response.body();
                    listener.onRetrieveSuccess(eras.getAges().getAgesListModel());
                }
                else
                {
                    int codeResponse = response.code();
                    listener.onRetrieveError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<AgesResponse> call, Throwable t)
            {
                listener.onRetrieveError(0, t);
            }
        });
    }
}
