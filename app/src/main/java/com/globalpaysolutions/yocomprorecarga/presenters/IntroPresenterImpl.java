package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IIntroPresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Intro;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.IntroView;

/**
 * Created by Josué Chávez on 30/10/2017.
 */

public class IntroPresenterImpl implements IIntroPresenter
{
    private static final String TAF = IntroPresenterImpl.class.getSimpleName();

    private Context mContext;
    private IntroView mView;

    public IntroPresenterImpl(Context context, AppCompatActivity activity, IntroView view)
    {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void initializeView()
    {
        mView.initialViewsState();
        mView.showTutorial();
    }

    @Override
    public void setIntroAsRead()
    {
        UserData.getInstance(mContext).saveHasReadIntro(true);
    }

    @Override
    public void navigateNext()
    {
        mView.navigateTermsConditions();
    }
}
