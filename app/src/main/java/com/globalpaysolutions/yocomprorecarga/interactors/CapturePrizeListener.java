package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public interface CapturePrizeListener
{
    void onRetrieveTracking(Tracking pTracking);
    void onTrackingError(int pCodeStatus, Throwable pThrowable);
    void onOpenChestSuccess(ExchangeResponse pExchangeResponse, int chestType, String firebaseID);
    void onOpenChestError(int pCodeStatus, Throwable pThrowable);
    void onRedeemPrizeSuccess(WinPrizeResponse pResponse);
    void onRedeemPrizeError(int pCodeStatus, Throwable pThrowable);
}
