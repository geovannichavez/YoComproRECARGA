package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IChallengesInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ChallengesResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public class ChallengesInteractor implements IChallengesInteractor
{
    private static final String TAG = ChallengesInteractor.class.getSimpleName();
    Context mContext;

    public ChallengesInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveChallenges(final ChallengesListener listener)
    {
        //TODO: Descomentar este
        /*ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<ChallengesResponse> call = apiService.getChallenges(UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);*/

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<ChallengesResponse> call = apiService.getChallenges("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.MTc2NzEzMzAxOTk2OTk4OQ.BnPh1-xchWJOoAA-9cpNiG1dN8eBKf_1kymvdRR8kgU",
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

        call.enqueue(new Callback<ChallengesResponse>()
        {
            @Override
            public void onResponse(Call<ChallengesResponse> call, Response<ChallengesResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onRetrieveSuccess(response.body());
                }
                else
                {
                    if(response.code() == 400)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onRetrieveError(response.code(), null, null, errorResponse);
                        }
                        catch (IOException ex)
                        {
                            Log.e(TAG, "Error trying to process Challenges List response");
                        }
                    }
                    else if(response.code() == 426)
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
            public void onFailure(Call<ChallengesResponse> call, Throwable t)
            {
                listener.onRetrieveError(0, t, null, null);
            }
        });
    }
}
