package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IChallengesInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.CombosResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;

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
    public void retrieveChallenges()
    {

    }
}
