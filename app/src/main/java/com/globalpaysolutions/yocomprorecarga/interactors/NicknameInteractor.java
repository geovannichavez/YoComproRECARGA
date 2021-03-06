package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.INicknameInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.NicknameReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.SimpleResultResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 11/07/2017.
 */

public class NicknameInteractor implements INicknameInteractor
{
    private static final String TAG = NicknameInteractor.class.getSimpleName();
    private NicknameListener mListener;
    private Context mContext;

    public NicknameInteractor(NicknameListener listener, Context context)
    {
        this.mListener = listener;
        this.mContext = context;
    }

    @Override
    public void validateNickname(String nickname)
    {
        final NicknameReqBody requestBody = new NicknameReqBody();
        requestBody.setNickname(nickname);
        UserData userData = UserData.getInstance(mContext);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<SimpleResultResponse> call = apiService.registerNickname(userData.getUserAuthenticationKey(), requestBody,
                getVersionName(), Constants.PLATFORM);

        call.enqueue(new Callback<SimpleResultResponse>()
        {
            @Override
            public void onResponse(Call<SimpleResultResponse> call, Response<SimpleResultResponse> response)
            {
                if(response.isSuccessful())
                {
                    SimpleResultResponse resultResponse = response.body();
                    mListener.onValidateNicknameSuccess(resultResponse, requestBody.getNickname());
                }
                else
                {
                    int codeResponse = response.code();
                    mListener.onError(codeResponse, null, null);

                }
            }
            @Override
            public void onFailure(Call<SimpleResultResponse> call, Throwable t)
            {
                mListener.onError(0, t, null);
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
