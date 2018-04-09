package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IMainInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.MainListener;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PendingsResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 12/3/2018.
 */

public class MainInteractor implements IMainInteractor
{
    private static final String TAG  = MainInteractor.class.getSimpleName();

    Context mContext;

    public MainInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrievePendings(final MainListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<PendingsResponse> call = apiService.getPendingChallenges(UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

        call.enqueue(new Callback<PendingsResponse>()
        {
            @Override
            public void onResponse(Call<PendingsResponse> call, Response<PendingsResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onRetrieveSucces(response.body());
                }
                else
                {
                    if(response.code() == 426)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onRetrieveError(response.code(), null, errorResponse.getInternalCode(), null);
                        }
                        catch (IOException ex)
                        {
                            Log.e(TAG, "Not a valid client version");
                        }
                    }
                    else
                    {
                        listener.onRetrieveError(response.code(), null, null, null);
                    }

                }
            }

            @Override
            public void onFailure(Call<PendingsResponse> call, Throwable t)
            {
                listener.onRetrieveError(0, t, null, null);
            }
        });
    }
}
