package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ILikesInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RequestRewardReq;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public class LikesInteractor implements ILikesInteractor
{
    private static final String TAG = LikesInteractor.class.getSimpleName();

    private Context mContext;

    public LikesInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void likeFanpage(final LikesListener listener)
    {
        try
        {
            new GraphRequest(AccessToken.getCurrentAccessToken(), Constants.FACEBOOK_FANPAGE_GRAPH_PATH,
                    null,
                    HttpMethod.POST,
                    new GraphRequest.Callback()
                    {
                        public void onCompleted(GraphResponse response)
                        {
                            /* handle the result */
                            if(response.getError() == null)
                                listener.onLikeSuccess();
                            else
                                listener.onLikeError();

                        }
                    }
            ).executeAsync();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on Like GraphAPI: " + ex.getMessage());
        }
    }

    @Override
    public void requestReward(final int option, final LikesListener listener)
    {
        try
        {
            RequestRewardReq request = new RequestRewardReq();
            request.setActionID(option);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<SimpleResponse> call = apiService.requestLikesReward(UserData.getInstance(mContext).getUserAuthenticationKey(),
                    VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, request);

            call.enqueue(new Callback<SimpleResponse>()
            {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response)
                {
                    if(response.isSuccessful())
                    {
                        listener.onRewardSuccess(response, option);
                    }
                    else
                    {
                        if(response.code() == 400)
                        {
                            try
                            {
                                Gson gson = new Gson();
                                SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                                listener.onRewardError(response.code(), null, null, errorResponse);
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
                                listener.onRewardError(response.code(), null, errorResponse.getInternalCode(), null);
                            }
                            catch (IOException ex)
                            {
                                Log.e(TAG, "Not a valid client version");
                            }
                        }
                        else
                        {
                            listener.onRewardError(response.code(), null, null, null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponse> call, Throwable t)
                {
                    listener.onRewardError(0, t, null, null);
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error requesting reward: " + ex.getMessage());
        }
    }
}
