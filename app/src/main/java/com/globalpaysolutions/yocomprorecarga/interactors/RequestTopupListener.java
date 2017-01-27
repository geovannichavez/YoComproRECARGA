package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;

import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public interface RequestTopupListener
{
    void onError(int pCodeStatus, Throwable pThrowable);
    void onGetOperatorsSuccess(List<CountryOperator> pCountryOperators);
    void onRequestTopupSuccess(SimpleMessageResponse pResponse);
}
