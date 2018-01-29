package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.BuildConfig;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IPrizesHistoryInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PrizesHistoryResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class PrizesHistoryInteractor implements IPrizesHistoryInteractor
{
    private static final String TAG = PrizesHistoryInteractor.class.getSimpleName();

    private Context mContext;
    private PrizesHistoryListener mListener;
    private UserData mUserData;

    public PrizesHistoryInteractor(Context context, PrizesHistoryListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void retrievePrizesHistory()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<PrizesHistoryResponse> call = apiService.retrievePrizsHistory(mUserData.getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM);

        call.enqueue(new Callback<PrizesHistoryResponse>()
        {
            @Override
            public void onResponse(Call<PrizesHistoryResponse> call, Response<PrizesHistoryResponse> response)
            {
                if (response.isSuccessful())
                {
                    PrizesHistoryResponse prizesHistory = response.body();
                    mListener.onRetrievePrizesSuccess(prizesHistory);
                }
                else
                {
                    try
                    {
                        int codeResponse = response.code();
                        if(codeResponse == 426)
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            mListener.onRetrievePrizesError(codeResponse, null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            mListener.onRetrievePrizesError(codeResponse, null, null);
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PrizesHistoryResponse> call, Throwable t)
            {
                mListener.onRetrievePrizesError(0, t, null);
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
