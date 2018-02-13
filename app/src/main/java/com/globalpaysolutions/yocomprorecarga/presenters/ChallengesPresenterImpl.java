package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ChallengesInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ChallengesListener;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ChallengesResponse;
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
        this.mInteractor = new ChallengesInteractor(mContext);
    }


    @Override
    public void retrieveChallenges()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveChallenges(this);
    }

    @Override
    public void initialize()
    {
        mView.initializeViews();
    }

    @Override
    public void onRetrieveSuccess(ChallengesResponse response)
    {
        mView.hideLoadingDialog();
        if(response != null)
            mView.renderChallegenes(response.getList());
    }

    @Override
    public void onRetrieveError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse)
    {
        mView.hideLoadingDialog();
    }
}
