package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.UpdateChallengeResponse;

/**
 * Created by Josué Chávez on 16/2/2018.
 */

public interface PlayChallengeListener
{
    void onCreateSuccess(SimpleResponse response);
    void onCreateChallengeError(SimpleResponse response, Throwable throwable, int codeStatus);
    void onUpdateSuccess(UpdateChallengeResponse body);
    void onUpdateChallengeError(SimpleResponse errorResponse, Throwable o, int codeResponse);
}
