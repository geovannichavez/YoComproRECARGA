package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.RequestTopupListener;
import com.globalpaysolutions.yocomprorecarga.models.api.RequestTopupReqBody;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public interface IRequestTopupInteractor
{
    void fetchOperators(RequestTopupListener pListener);
    void sendTopupRequest(RequestTopupListener pListener, RequestTopupReqBody pRequestTopup);
}
