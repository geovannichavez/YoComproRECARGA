package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.coreui.BuildConfig;
import android.util.Log;

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
    private static final String TAG = AchievementsInteractor.class.getSimpleName();
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
                getVersionName(), Constants.PLATFORM);

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

    private String getVersionName()
    {
        String version = "";
        try
        {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            version = pInfo.versionName;//Version Name
            Log.i(TAG, "Version name: " + version);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Could not retrieve version name: " + ex.getMessage());
        }

        return version;
    }
}
