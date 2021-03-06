package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ITriviaInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RespondTriviaReq;
import com.globalpaysolutions.yocomprorecarga.models.api.RespondTriviaResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TriviaResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 6/3/2018.
 */

public class TriviaInteractor implements ITriviaInteractor
{
    private static final String TAG = TriviaInteractor.class.getSimpleName();

    private Context mContext;

    public TriviaInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void requestTrivia(final TriviaListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<TriviaResponse> call = apiService.getTrivia(UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM);

        call.enqueue(new Callback<TriviaResponse>()
        {
            @Override
            public void onResponse(Call<TriviaResponse> call, Response<TriviaResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onRetriveTriviaSuccess(response.body());
                }
                else
                {
                    if(response.code() == 400)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onRetriveTriviaError(response.code(), null, null, errorResponse);
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
                            listener.onRetriveTriviaError(response.code(), null, errorResponse.getInternalCode(), null);
                        }
                        catch (IOException ex)
                        {
                            Log.e(TAG, "Not a valid client version");
                        }
                    }
                    else
                    {
                        listener.onRetriveTriviaError(response.code(), null, null, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<TriviaResponse> call, Throwable t)
            {
                listener.onRetriveTriviaError(0, t, null, null);
            }
        });
    }

    @Override
    public void answerTrivia(final int answerID, final TriviaListener listener, final int buttonClicked, int triviaID, int points)
    {
        RespondTriviaReq request = new RespondTriviaReq();
        request.setTriviaAnswerID(answerID);
        request.setTriviaID(triviaID);
        request.setPoints(points);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<RespondTriviaResponse> call = apiService.respondTrivia(UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, request);

        call.enqueue(new Callback<RespondTriviaResponse>()
        {
            @Override
            public void onResponse(Call<RespondTriviaResponse> call, Response<RespondTriviaResponse> response)
            {
                if(response.isSuccessful())
                {
                    listener.onAnswerSuccess(response.body(), answerID, buttonClicked);
                }
                else
                {
                    if(response.code() == 400)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onAnswerError(response.code(), null, null, errorResponse);
                        }
                        catch (IOException ex)
                        {
                            Log.e(TAG, "Error trying to process Trivia Answer response");
                        }
                    }
                    else if(response.code() == 426)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onAnswerError(response.code(), null, errorResponse.getInternalCode(), null);
                        }
                        catch (IOException ex)
                        {
                            Log.e(TAG, "Not a valid client version");
                        }
                    }
                    else
                    {
                        listener.onAnswerError(response.code(), null, null, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespondTriviaResponse> call, Throwable t)
            {
                listener.onAnswerError(0, t, null, null);
            }
        });


    }

    @Override
    public void silentAnswerTrivia(int answer, final TriviaListener listener, int triviaID)
    {
        RespondTriviaReq request = new RespondTriviaReq();
        request.setTriviaAnswerID(answer);
        request.setTriviaID(triviaID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<RespondTriviaResponse> call = apiService.respondTrivia(UserData.getInstance(mContext).getUserAuthenticationKey(),
                VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, request);

        call.enqueue(new Callback<RespondTriviaResponse>()
        {
            @Override
            public void onResponse(Call<RespondTriviaResponse> call, Response<RespondTriviaResponse> response)
            {
                listener.onSilentAnswerSuccess();
            }

            @Override
            public void onFailure(Call<RespondTriviaResponse> call, Throwable t)
            {
                listener.onSilentAnswerError();
            }
        });
    }
}
