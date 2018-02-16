package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IPlayChallengeInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.CreateChallengeReq;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 16/2/2018.
 */

public class PlayChallengeInteractor implements IPlayChallengeInteractor
{
    private static final String TAG = PlayChallengeInteractor.class.getSimpleName();

    Context mContext;

    public PlayChallengeInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void createChallenge(String playerID, int move, double bet, final PlayChallengeListener listener)
    {
        CreateChallengeReq request = new CreateChallengeReq();
        request.setSelectOption(move);
        request.setBet(bet);
        request.setOpponentID(playerID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<SimpleResponse> call = apiService.createChallenge(UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, request);

        call.enqueue(new Callback<SimpleResponse>()
        {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onCreateSuccess(response.body());
                }
                else
                {
                    int codeResponse = response.code();

                    if(codeResponse == 426)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onCreateChallengeError(errorResponse, null, codeResponse);
                        }
                        catch (IOException ex)
                        {
                            ex.printStackTrace();

                        }
                    }
                    else
                    {
                        listener.onCreateChallengeError(response.body(), null, codeResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t)
            {
                listener.onCreateChallengeError(null, t, 0);
            }
        });
    }
}
