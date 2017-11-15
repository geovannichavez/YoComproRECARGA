package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IErasInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionReq;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionResponse;
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
    public void eraSelection(int eraID, final ErasListener listener)
    {
        EraSelectionReq request = new EraSelectionReq();
        request.setAgeID(eraID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<EraSelectionResponse> call = apiService.selectEra(mUserData.getUserAuthenticationKey(), request);

        call.enqueue(new Callback<EraSelectionResponse>()
        {
            @Override
            public void onResponse(Call<EraSelectionResponse> call, Response<EraSelectionResponse> response)
            {
                if (response.isSuccessful())
                {
                    EraSelectionResponse eraSelection = response.body();
                    listener.onEraSelectionSuccess(eraSelection);
                }
                else
                {
                    int codeResponse = response.code();
                    listener.onEraSelectionError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<EraSelectionResponse> call, Throwable t)
            {
                listener.onEraSelectionError(0, t);
            }
        });
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
