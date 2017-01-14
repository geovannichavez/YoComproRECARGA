package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.Countries;


/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface ValidatePhoneListener
{
    void onError(int pCodeStatus, Throwable pThrowable);
    void onGetCountriesSuccess(Countries pCountries);
    void onValidatePhoneSuccess();
}
