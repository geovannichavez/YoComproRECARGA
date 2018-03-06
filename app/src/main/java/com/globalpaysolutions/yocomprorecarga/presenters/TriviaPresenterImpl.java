package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.interactors.TriviaInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.TriviaListener;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TriviaResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ITriviaPresenter;
import com.globalpaysolutions.yocomprorecarga.views.TriviaView;

/**
 * Created by Josué Chávez on 5/3/2018.
 */

public class TriviaPresenterImpl implements ITriviaPresenter, TriviaListener
{
    private static final String TAG = TriviaPresenterImpl.class.getSimpleName();

    private Context mContext;
    private TriviaView mView;
    private AppCompatActivity mActivity;
    private TriviaInteractor mInteractor;

    public TriviaPresenterImpl(Context context, TriviaView view, AppCompatActivity activity)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new TriviaInteractor(mContext);
    }

    @Override
    public void initialize()
    {
        mView.initilizeViews();
    }

    @Override
    public void requestTrivia()
    {
        try
        {
            mInteractor.requestTrivia(this);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error requesting trivia: " + ex.getMessage());
        }
    }

    @Override
    public void onRetriveTriviaSuccess(TriviaResponse response)
    {

    }

    @Override
    public void onRetriveTriviaError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse)
    {

    }
}
