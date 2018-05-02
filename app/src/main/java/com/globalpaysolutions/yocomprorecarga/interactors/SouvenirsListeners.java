package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvsProgressResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public interface SouvenirsListeners
{
    //void onSuccess(List<ListSouvenirsByConsumer> souvenirs);
    void onSuccess(JsonObject responseRaw);
    void onError(int codeStatus, Throwable throwable, String requiredVersion);
    void onExchangeSouvSuccess(WinPrizeResponse redeemPrize);
    void onExchangeSouvError(int codeResponse, Throwable throwable, String requiredVersion);
    void onGetProgressSuccess(SouvsProgressResponse response);
    void onGetProgressError(int codeStatus, Throwable throwable, SimpleResponse response);
}
