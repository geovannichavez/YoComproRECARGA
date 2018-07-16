package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.INewsInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.FeedReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.NewsResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsInteractor implements INewsInteractor
{
    private static final String TAG = NewsInteractor.class.getSimpleName();
    private Context mContext;

    public NewsInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveNews(final NewsListener listener)
    {
        try
        {
            FeedReqBody reqBody = new FeedReqBody();
            reqBody.setOption(1);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<NewsResponse> call = apiService.getFeed(UserData.getInstance(mContext).getUserAuthenticationKey(),
                    VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, reqBody);

            call.enqueue(new Callback<NewsResponse>()
            {
                @Override
                public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response)
                {
                    if(response.isSuccessful())
                    {
                        listener.onRetrieveSuccess(response.body());
                    }
                    else
                    {
                        int codeResponse = response.code();
                        try
                        {
                            if(codeResponse == 426)
                            {
                                Gson gson = new Gson();
                                SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                                listener.onRetrieveError(codeResponse, null, errorResponse.getInternalCode(), "");
                            }
                            else
                            {
                                String rawResponse = response.errorBody().string();
                                Log.i(TAG, rawResponse);
                                listener.onRetrieveError(codeResponse, null, null, rawResponse);
                            }
                        }
                        catch (IOException ex)
                        {
                            Log.e(TAG, "Error: " + ex.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t)
                {
                    listener.onRetrieveError(0, t, null, null);
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
