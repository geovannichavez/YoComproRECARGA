package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;

import java.util.List;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public interface ErasListener
{
    void onRetrieveSuccess(List<AgesListModel> eras);
    void onRetrieveError(int pCodeStatus, Throwable pThrowable);
}
