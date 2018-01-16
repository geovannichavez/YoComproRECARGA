package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICombosPresenter;
import com.globalpaysolutions.yocomprorecarga.views.CombosView;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public class CombosPresenterImpl implements ICombosPresenter
{
    private Context mContext;
    private CombosView mView;

    public CombosPresenterImpl(Context context, AppCompatActivity activity, CombosView view)
    {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void retrieveCombos()
    {

    }
}
