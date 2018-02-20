package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.models.ChallengeResultData;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IChallengeResultPresenter;
import com.globalpaysolutions.yocomprorecarga.views.ChallengeResultView;

/**
 * Created by Josué Chávez on 19/2/2018.
 */

public class ChallengeResultPresenterImpl implements IChallengeResultPresenter
{
    private static final String TAG = ChallengeResultPresenterImpl.class.getSimpleName();

    private Context mContext;
    private ChallengeResultView mView;

    public ChallengeResultPresenterImpl(Context context, AppCompatActivity activity, ChallengeResultView view)
    {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void initializeViews(ChallengeResultData data)
    {
        mView.setClickListeners();

        if(data != null)
            mView.iniatializeViews(data);
    }
}
