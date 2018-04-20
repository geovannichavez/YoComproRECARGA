package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IProfileInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 10/4/2018.
 */

public class ProfileInteractor implements IProfileInteractor
{
    private static final String TAG = ProfileInteractor.class.getSimpleName();

    private Context mContext;

    public ProfileInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveTracking(final ProfileListener listener)
    {
        try
        {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<Tracking> call = apiService.getConsumerTracking(UserData.getInstance(mContext).getUserAuthenticationKey(),
                    VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

            call.enqueue(new Callback<Tracking>()
            {
                @Override
                public void onResponse(Call<Tracking> call, Response<Tracking> response)
                {
                    if(response.isSuccessful())
                    {
                        listener.onRetrieveTrackingSuccess(response.body());
                    }
                    else
                    {
                        if(response.code() == 400 || response.code() == 426)
                        {
                            try
                            {
                                Gson gson = new Gson();
                                SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                                listener.onRetrieveTrackingError(response.code(), null,  errorResponse);
                            }
                            catch (IOException ex)
                            {
                                Log.e(TAG, "Error response: " + ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tracking> call, Throwable t)
                {
                    listener.onRetrieveTrackingError(0, t,  null);
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error retrieving tracking: " + ex.getMessage());
        }
    }

    @Override
    public void retrieveProfilePicture(String url)
    {

    }
}
