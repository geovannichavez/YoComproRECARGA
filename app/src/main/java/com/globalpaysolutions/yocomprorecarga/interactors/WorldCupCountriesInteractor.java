package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IWorldCupCountriesInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WorldCupCountriesRspns;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public class WorldCupCountriesInteractor implements IWorldCupCountriesInteractor
{
    private static final String TAG = WorldCupCountriesInteractor.class.getSimpleName();

    private Context mContext;

    public WorldCupCountriesInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveCountries(final WorldCupCountriesListener listener)
    {
        try
        {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<WorldCupCountriesRspns> call = apiService.retrieveWorldcupCountries(
                    UserData.getInstance(mContext).getUserAuthenticationKey(),
                    VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

            call.enqueue(new Callback<WorldCupCountriesRspns>()
            {
                @Override
                public void onResponse(Call<WorldCupCountriesRspns> call, Response<WorldCupCountriesRspns> response)
                {
                    if(response.isSuccessful())
                    {
                        listener.onRetrieveSuccess(response.body());
                    }
                    else
                    {
                        try
                        {
                            if(response.code() == 426 || response.code() == 429 || response.code() == 500 )
                            {
                                Gson gson = new Gson();
                                SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                                listener.onRetrieveError(response.code(), null, errorResponse);
                            }

                        }
                        catch (Exception ex)
                        {
                            Log.e(TAG, "Error: " +ex.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<WorldCupCountriesRspns> call, Throwable t)
                {
                    listener.onRetrieveError(0, t, null);
                }
            });

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
