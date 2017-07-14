package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public interface CapturePrizeListener
{
    void onRetrieveTracking(Tracking pTracking);
    void onTrackingError(int pCodeStatus, Throwable pThrowable);
    void onExchangeChestSuccess(ExchangeResponse pExchangeResponse);
    void onExchangeError(int pCodeStatus, Throwable pThrowable);
}
