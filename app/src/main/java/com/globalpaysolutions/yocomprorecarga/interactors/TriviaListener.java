package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RespondTriviaResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TriviaResponse;

/**
 * Created by Josué Chávez on 6/3/2018.
 */

public interface TriviaListener
{
    void onRetriveTriviaSuccess(TriviaResponse response);
    void onRetriveTriviaError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse);
    void onAnswerSuccess(RespondTriviaResponse response, int answerID, int buttonClicked);
    void onAnswerError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse);
}
