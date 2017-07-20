package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ILeaderboardsInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.LeaderboardReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.LeaderboardsResponse;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 19/07/2017.
 */

public class LeaderboardsInteractor implements ILeaderboardsInteractor
{
    private static final String TAG = LeaderboardsInteractor.class.getSimpleName();

    private Context mContext;
    private UserData mUserData;
    private LeaderboardsListener mListener;

    public LeaderboardsInteractor(Context context, LeaderboardsListener listener)
    {
        this.mContext = context;
        this.mUserData = UserData.getInstance(mContext);
        this.mListener = listener;
    }

    @Override
    public void retrieveLeaderboard(String interval)
    {
        LeaderboardReqBody request = new LeaderboardReqBody();
        request.setSearch(interval);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<LeaderboardsResponse> call = apiService.retrieveLeaderboards(mUserData.getUserAuthenticationKey(), request);


        call.enqueue(new Callback<LeaderboardsResponse>()
        {
            @Override
            public void onResponse(Call<LeaderboardsResponse> call, Response<LeaderboardsResponse> response)
            {
                if (response.isSuccessful())
                {

                    LeaderboardsResponse leaderboards = response.body();
                    mListener.onSuccess(leaderboards);
                }
                else
                {
                    int codeResponse = response.code();
                    mListener.onError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<LeaderboardsResponse> call, Throwable t)
            {
                mListener.onError(0, t);
            }
        });
    }
}
