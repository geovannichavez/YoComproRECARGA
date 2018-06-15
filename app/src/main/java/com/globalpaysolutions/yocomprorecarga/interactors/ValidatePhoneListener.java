package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;


/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface ValidatePhoneListener
{
    void onError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion, String rawResponse);
    void onGetCountriesSuccess(Countries pCountries);
    void onRequestPhoneValResult(RegisterClientResponse pResponse, int codeStatus );
    void onAuthLocalSuccess(RegisterClientResponse message, int code);
}
