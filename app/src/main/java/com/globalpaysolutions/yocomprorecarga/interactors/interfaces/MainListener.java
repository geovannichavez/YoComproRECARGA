package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PendingsResponse;

/**
 * Created by Josué Chávez on 12/3/2018.
 */

public interface MainListener
{
    void onRetrieveSucces(PendingsResponse response);
    void onRetrieveError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse);
}
