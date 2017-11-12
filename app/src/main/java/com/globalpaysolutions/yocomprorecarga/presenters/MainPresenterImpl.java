package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IMainPresenter;
import com.globalpaysolutions.yocomprorecarga.views.MainView;

/**
 * Created by Josué Chávez on 06/11/2017.
 */

public class MainPresenterImpl implements IMainPresenter
{
    private static final String TAG = MainPresenterImpl.class.getSimpleName();

    private Context mContext;
    private MainView mView;

    public MainPresenterImpl(Context context, AppCompatActivity activity, MainView view)
    {
        this.mContext = context;
        this.mView = view;
    }


    @Override
    public void hideStatusBar()
    {
        mView.hideStatusBar();
    }
}
