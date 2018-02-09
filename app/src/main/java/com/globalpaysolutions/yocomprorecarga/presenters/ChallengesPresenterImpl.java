package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ChallengesInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ChallengesListener;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IChallengesPresenter;
import com.globalpaysolutions.yocomprorecarga.views.ChallengesView;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public class ChallengesPresenterImpl implements IChallengesPresenter, ChallengesListener
{
    private static final String TAG = ChallengesPresenterImpl.class.getSimpleName();

    private Context mContext;
    private ChallengesView mView;
    private ChallengesInteractor mInteractor;

    public ChallengesPresenterImpl(Context context, ChallengesView view, AppCompatActivity activity)
    {
        this.mContext = context;
        this.mView = view;
    }


    @Override
    public void retrieveChallenges()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveChallenges();
    }

    @Override
    public void initializeViews()
    {

    }

    @Override
    public void onRetrieveSuccess()
    {

    }

    @Override
    public void onRetrieveError()
    {

    }
}
