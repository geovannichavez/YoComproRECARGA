package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.ICompleteProfileInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.CompleteProfileReqBody;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.utils.VersionName;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteProfileInteractor implements ICompleteProfileInteractor
{
    private static final String TAG = CompleteProfileInteractor.class.getSimpleName();

    private Context mContext;

    public CompleteProfileInteractor(Context context)
    {
        mContext = context;
    }

    @Override
    public void completeProfileLocalAuth(final CompleteProfileListener listener, final String firstname, final String lastname, final String nickname)
    {
        try
        {
            CompleteProfileReqBody reqBody = new CompleteProfileReqBody();
            reqBody.setFirstname(firstname);
            reqBody.setLastname(lastname);
            reqBody.setNickname(nickname);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<SimpleResponse> call = apiService.completeLocalProfile(UserData.getInstance(mContext).getUserAuthenticationKey(),
                    VersionName.getVersionName(mContext, TAG), Constants.PLATFORM, reqBody);

            call.enqueue(new Callback<SimpleResponse>()
            {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response)
                {
                    if(response.isSuccessful())
                    {
                        SimpleResponse resultResponse = response.body();
                        listener.onCompleteProfileSucces(resultResponse, nickname, firstname, lastname);
                    }
                    else
                    {
                        int codeResponse = response.code();
                        listener.onCompleteProfileError(codeResponse, null, response.message());

                    }
                }

                @Override
                public void onFailure(Call<SimpleResponse> call, Throwable t)
                {
                    listener.onCompleteProfileError(0, t, null);
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error completeProfileLocalAuth: " + ex.getMessage());
        }
    }

}
