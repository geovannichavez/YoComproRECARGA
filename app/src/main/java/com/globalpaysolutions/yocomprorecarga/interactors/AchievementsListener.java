package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.AchievementsResponse;

/**
 * Created by Josué Chávez on 16/11/2017.
 */

public interface AchievementsListener
{
    void onRetrieveSuccess(AchievementsResponse response);
    void onRetrieveError(int pCodeStatus, Throwable pThrowable, String requiredVersion);
}
