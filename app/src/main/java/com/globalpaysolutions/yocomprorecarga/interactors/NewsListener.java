package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.NewsResponse;

public interface NewsListener
{
    void onRetrieveSuccess(NewsResponse response);
    void onRetrieveError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion, String rawResponse);
}
