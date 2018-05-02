package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.LeaderboardsResponse;

/**
 * Created by Josué Chávez on 19/07/2017.
 */

public interface LeaderboardsListener
{
    void onSuccess(LeaderboardsResponse response);
    void onError(int code, Throwable throwable);
}
