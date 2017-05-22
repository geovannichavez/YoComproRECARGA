package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TrackingResponse;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public interface CapturePrizeListener
{
    void onRetrieveTracking(TrackingResponse pTracking);
    void onTrackingError(int pCodeStatus, Throwable pThrowable);
    void onExchangeCoinSuccess(ExchangeResponse pExchangeResponse);
    void onExchangeError(int pCodeStatus, Throwable pThrowable);
}
