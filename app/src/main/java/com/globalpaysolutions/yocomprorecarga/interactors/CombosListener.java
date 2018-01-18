package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.CombosResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public interface CombosListener
{
    void onRetrieveSuccess(CombosResponse souvenirs);
    void onRetrieveError(int code, Throwable throwable, String internalCode);
    void onExchangeSouvSuccess(WinPrizeResponse redeemPrize);
    void onExchangeComboError(SimpleResponse errorResponse, int codeStatus, Throwable throwable);
    void onAcceptedResponse();
}
