package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.PrizesHistoryResponse;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public interface PrizesHistoryListener
{
    void onRetrievePrizesSuccess(PrizesHistoryResponse response);
    void onRetrievePrizesError(int code, Throwable throwable, String requiredVersion);
}
