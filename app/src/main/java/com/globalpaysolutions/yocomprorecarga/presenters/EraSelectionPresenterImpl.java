package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.interactors.ErasInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ErasListener;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IEraSelectionPresenter;
import com.globalpaysolutions.yocomprorecarga.views.EraSelectionView;

import java.util.List;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class EraSelectionPresenterImpl implements IEraSelectionPresenter, ErasListener
{
    private static final String TAG = EraSelectionPresenterImpl.class.getSimpleName();

    private Context mContext;
    private EraSelectionView mView;
    private ErasInteractor mInteractor;

    public EraSelectionPresenterImpl(Context context, AppCompatActivity activity, EraSelectionView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new ErasInteractor(mContext);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void retrieveEras()
    {
        mInteractor.retrieveEras(this);
    }

    @Override
    public void onRetrieveSuccess(List<AgesListModel> eras)
    {
        mView.renderEras(eras);
    }

    @Override
    public void onRetrieveError(int pCodeStatus, Throwable pThrowable)
    {

    }
}
