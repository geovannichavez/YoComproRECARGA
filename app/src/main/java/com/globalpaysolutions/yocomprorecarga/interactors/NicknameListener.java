package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.SimpleResultResponse;

/**
 * Created by Josué Chávez on 11/07/2017.
 */

public interface NicknameListener
{
    void onValidateNicknameSuccess(SimpleResultResponse pResultResponse);
    void onError(int pCodeStatus, Throwable pThrowable);
}
