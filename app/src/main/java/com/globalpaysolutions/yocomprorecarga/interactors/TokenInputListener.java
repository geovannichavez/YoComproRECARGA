package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ValidateLocalSmsResponse;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public interface TokenInputListener
{
    void onError(int pCodeStatus, Throwable pThrowable);
    void onValidationTokenResult(SimpleMessageResponse pResponse);
    void onValidationTokenLocalResult(ValidateLocalSmsResponse response);
}
