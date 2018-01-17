package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.CombosResponse;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public interface CombosListener
{
    void onRetrieveSuccess(CombosResponse souvenirs);
    void onRetrieveError(int code, Throwable throwable, String internalCode);
}
