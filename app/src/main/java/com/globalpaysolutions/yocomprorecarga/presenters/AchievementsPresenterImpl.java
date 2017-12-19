package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.AchievementsInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.AchievementsListener;
import com.globalpaysolutions.yocomprorecarga.models.api.AchievementsResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IAchievementsPresenter;
import com.globalpaysolutions.yocomprorecarga.views.AchievementsView;

/**
 * Created by Josué Chávez on 16/11/2017.
 */

public class AchievementsPresenterImpl implements IAchievementsPresenter, AchievementsListener
{
    private static final String TAG = AchievementsPresenterImpl.class.getSimpleName();

    private Context mContext;
    private AchievementsView mView;
    private AchievementsInteractor mInteractor;

    public AchievementsPresenterImpl(Context context, AppCompatActivity activity, AchievementsView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new AchievementsInteractor(mContext);
    }

    @Override
    public void retrieveAchievements()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveAchievements(this);
    }

    @Override
    public void loadBackground()
    {
        mView.loadBackground();
    }

    @Override
    public void onRetrieveSuccess(AchievementsResponse response)
    {
        try
        {
            mView.hideLoadingDialog();
            mView.renderAchievements(response.getListAchievementsByConsumer());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRetrieveError(int pCodeStatus, Throwable pThrowable)
    {
        mView.hideLoadingDialog();
    }
}
