package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WorldCupCountriesRspns;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public interface WorldCupCountriesListener
{
    void onRetrieveSuccess(WorldCupCountriesRspns response);
    void onRetrieveError(int codeStatus, Throwable throwable, SimpleResponse response);
}
