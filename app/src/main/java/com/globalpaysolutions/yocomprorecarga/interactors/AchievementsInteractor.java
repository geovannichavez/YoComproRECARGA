package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IAchievementsInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.AchievementsResponse;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 16/11/2017.
 */

public class AchievementsInteractor implements IAchievementsInteractor
{
    private Context mContext;

    public AchievementsInteractor(Context context)
    {
        this.mContext = context;
    }


    @Override
    public void retrieveAchievements(final AchievementsListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<AchievementsResponse> call = apiService.getAchievements(UserData.getInstance(mContext).getUserAuthenticationKey());

        call.enqueue(new Callback<AchievementsResponse>()
        {
            @Override
            public void onResponse(Call<AchievementsResponse> call, Response<AchievementsResponse> response)
            {
                if(response.isSuccessful())
                {
                    AchievementsResponse achievementsResponse = response.body();
                    listener.onRetrieveSuccess(achievementsResponse);
                }
                else
                {
                    listener.onRetrieveError(response.code(), null);
                }
            }

            @Override
            public void onFailure(Call<AchievementsResponse> call, Throwable t)
            {
                listener.onRetrieveError(0, t);
            }
        });
    }
}
