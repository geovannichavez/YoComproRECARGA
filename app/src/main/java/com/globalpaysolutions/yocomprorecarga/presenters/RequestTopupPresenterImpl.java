package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.interactors.RequestTopupInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.RequestTopupListener;
import com.globalpaysolutions.yocomprorecarga.interactors.ValidatePhoneInteractor;
import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.globalpaysolutions.yocomprorecarga.views.RequestTopupView;
import com.globalpaysolutions.yocomprorecarga.views.ValidatePhoneView;

import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public class RequestTopupPresenterImpl implements IRequestTopupPresenter, RequestTopupListener
{
    private RequestTopupView view;
    private RequestTopupInteractor interactor;
    private Context context;

    public RequestTopupPresenterImpl(RequestTopupView pView, AppCompatActivity pActivity, Context pContext)
    {
        this.view = pView;
        this.interactor = new RequestTopupInteractor();
        this.context = pContext;
    }

    @Override
    public void onError(int pCodeStatus, Throwable pThrowable)
    {

    }

    @Override
    public void onGetOperatorsSuccess(List<CountryOperator> pCountryOperators)
    {
        this.view.renderOperators(pCountryOperators);
    }

    @Override
    public void setInitialViewState()
    {
        this.view.initialViewsState();
    }

    @Override
    public void fetchOperators()
    {
        this.interactor.fetchOperators(this);
    }
}
