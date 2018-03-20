package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RewardResponse;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public interface LikesListener
{
    void onRewardSuccess(RewardResponse response, int option);
    void onRewardError(int code, Throwable throwable, String requiredVersion, SimpleResponse errorResponse);
}
