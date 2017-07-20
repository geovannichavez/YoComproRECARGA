package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IPrizesHistoryInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.PrizesHistoryResponse;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class PrizesHistoryInteractor implements IPrizesHistoryInteractor
{
    private static final String TAG = PrizesHistoryInteractor.class.getSimpleName();

    private Context mContext;
    private PrizesHistoryListener mListener;
    private UserData mUserData;

    public PrizesHistoryInteractor(Context context, PrizesHistoryListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void retrievePrizesHistory()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<PrizesHistoryResponse> call = apiService.retrievePrizsHistory(mUserData.getUserAuthenticationKey());

        call.enqueue(new Callback<PrizesHistoryResponse>()
        {
            @Override
            public void onResponse(Call<PrizesHistoryResponse> call, Response<PrizesHistoryResponse> response)
            {
                if (response.isSuccessful())
                {

                    PrizesHistoryResponse prizesHistory = response.body();
                    mListener.onRetrievePrizesSuccess(prizesHistory);
                }
                else
                {
                    int codeResponse = response.code();
                    mListener.onRetrievePrizesError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<PrizesHistoryResponse> call, Throwable t)
            {
                mListener.onRetrievePrizesError(0, t);
            }
        });
    }
}
