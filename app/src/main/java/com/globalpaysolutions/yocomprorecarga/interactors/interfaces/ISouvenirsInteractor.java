package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.SouvenirsListeners;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeSouvenirReq;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public interface ISouvenirsInteractor
{
    void requestUserSouvenirs(SouvenirsListeners listener);
    void atemptExchangeSouv(SouvenirsListeners listener, int souvenirID);
}
