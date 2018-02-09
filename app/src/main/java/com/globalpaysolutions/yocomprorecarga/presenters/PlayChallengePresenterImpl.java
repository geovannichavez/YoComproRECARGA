package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IPlayChallengePresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.PlayChallenge;
import com.globalpaysolutions.yocomprorecarga.views.PlayChallengeView;

/**
 * Created by Josué Chávez on 9/2/2018.
 */

public class PlayChallengePresenterImpl implements IPlayChallengePresenter
{
    private static final String TAG = PlayChallengePresenterImpl.class.getSimpleName();

    private Context mContext;
    private PlayChallengeView mView;

    public PlayChallengePresenterImpl(Context context, PlayChallengeView view, PlayChallenge activity)
    {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void getIncomingChallengeDetail()
    {

    }

    @Override
    public void challengePlayer()
    {

    }

    @Override
    public void respondChallenge()
    {

    }
}
