package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;

/**
 * Created by Josué Chávez on 16/2/2018.
 */

public interface PlayChallengeListener
{
    void onCreateSuccess(SimpleResponse response);
    void onCreateChallengeError(SimpleResponse response, Throwable throwable, int codeStatus);
}
