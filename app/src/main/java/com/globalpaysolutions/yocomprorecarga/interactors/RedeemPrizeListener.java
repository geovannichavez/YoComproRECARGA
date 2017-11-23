package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.ActivatePrizeResponse;

/**
 * Created by Josué Chávez on 22/11/2017.
 */

public interface RedeemPrizeListener
{
    void onSuccess(ActivatePrizeResponse response);
    void onError(int statusCode, Throwable throwable);
}
