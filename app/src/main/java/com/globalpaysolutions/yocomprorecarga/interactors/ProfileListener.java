package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;

/**
 * Created by Josué Chávez on 10/4/2018.
 */

public interface ProfileListener
{
    void onRetrieveTrackingSuccess(Tracking tracking);
    void onRetrieveTrackingError(int codeStatus, Throwable throwable, SimpleResponse errorResponse);
}
