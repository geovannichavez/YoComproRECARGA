package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.interactors.SouvenirsInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.SouvenirsListeners;
import com.globalpaysolutions.yocomprorecarga.models.api.ListAchievementsByConsumer;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ISourvenirsPresenter;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsView;

import java.util.List;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public class SourvenirsPresenterImpl implements ISourvenirsPresenter, SouvenirsListeners
{
    private static final String TAG = SourvenirsPresenterImpl.class.getSimpleName();

    private Context mContext;
    private SouvenirsView mView;
    private SouvenirsInteractor mInteractor;

    public SourvenirsPresenterImpl(Context context, AppCompatActivity activity, SouvenirsView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new SouvenirsInteractor(mContext);
    }

    @Override
    public void requestSouvenirs()
    {
        mInteractor.requestUserSouvenirs(this);
    }

    @Override
    public void onSuccess(List<ListSouvenirsByConsumer> souvenirs)
    {
        mView.renderSouvenirs(souvenirs);
    }

    @Override
    public void onError(int codeStatus, Throwable throwable)
    {

    }
}
