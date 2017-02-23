package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.globalpaysolutions.yocomprorecarga.models.api.RequestTopupReqBody;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public interface IRequestTopupPresenter
{
    void setInitialViewState();
    void fetchOperators();
    void selectAmount(CountryOperator pOperator);
    void onOperatorSelected(int pPosition);
    RequestTopupReqBody createRequestTopupObject();
    void sendTopupRequest();
    void refreshOperators();
}
