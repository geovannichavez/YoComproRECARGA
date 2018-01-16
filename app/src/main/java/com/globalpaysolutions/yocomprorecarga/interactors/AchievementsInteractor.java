package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.support.coreui.BuildConfig;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IAchievementsInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Achievement;
import com.globalpaysolutions.yocomprorecarga.models.api.AchievementsResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.gson.Gson;

import java.io.IOException;

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
        final Call<AchievementsResponse> call = apiService.getAchievements(UserData.getInstance(mContext).getUserAuthenticationKey(),
                BuildConfig.VERSION_NAME, Constants.PLATFORM);

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
                   try
                   {
                       if(response.code() == 426)
                       {
                           Gson gson = new Gson();
                           SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                           listener.onRetrieveError(response.code(), null, errorResponse.getInternalCode());
                       }
                       else
                       {
                           listener.onRetrieveError(response.code(), null, null);
                       }
                   }
                   catch (IOException ex)
                   {
                       ex.printStackTrace();
                   }
                }
            }
            @Override
            public void onFailure(Call<AchievementsResponse> call, Throwable t)
            {
                listener.onRetrieveError(0, t, null);
            }

        });
    }
}
