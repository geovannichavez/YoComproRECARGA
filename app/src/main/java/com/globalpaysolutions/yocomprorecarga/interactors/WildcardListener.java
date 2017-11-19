package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeWildcardResponse;

/**
 * Created by Josué Chávez on 18/11/2017.
 */

public interface WildcardListener
{
    void onExchangeWildcardSuccess(ExchangeWildcardResponse response);
    void onExchangeWildcardError(int codeStatus, Throwable throwable);
}
