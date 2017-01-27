package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.interactors.RequestTopupInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.RequestTopupListener;
import com.globalpaysolutions.yocomprorecarga.models.Amount;
import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IRequestTopupPresenter;
import com.globalpaysolutions.yocomprorecarga.views.RequestTopupView;

import java.util.ArrayList;
import java.util.HashMap;
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
        this.context = pContext;
        this.interactor = new RequestTopupInteractor(context);
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
    public void onRequestTopupSuccess(SimpleMessageResponse pResponse)
    {

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

    @Override
    public void selectAmount(CountryOperator pOperator)
    {
        List<String> amountNames = new ArrayList<>();
        HashMap<String, Amount> amountsMap = new HashMap<>();

        for(Amount amount : pOperator.getAmounts())
        {
            amountNames.add(amount.getDescription());
            amountsMap.put(amount.getDescription(), amount);
        }
        this.view.showAmounts(amountNames, amountsMap);
    }

    @Override
    public void onOperatorSelected(int pPosition)
    {
        this.view.setSelectedOperator(pPosition);
        this.view.resetAmount();
    }


}
