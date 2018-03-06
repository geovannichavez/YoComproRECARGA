package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ITriviaPresenter;
import com.globalpaysolutions.yocomprorecarga.views.TriviaView;

/**
 * Created by Josué Chávez on 5/3/2018.
 */

public class TriviaPresenterImpl implements ITriviaPresenter
{
    private Context mContext;
    private TriviaView mView;
    private AppCompatActivity mActivity;

    public TriviaPresenterImpl(Context context, TriviaView view, AppCompatActivity activity)
    {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void initialize()
    {
        mView.initilizeViews();
    }
}
