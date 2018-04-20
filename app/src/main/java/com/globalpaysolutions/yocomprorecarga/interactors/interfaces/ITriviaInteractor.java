package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.TriviaListener;

/**
 * Created by Josué Chávez on 6/3/2018.
 */

public interface ITriviaInteractor
{
    void requestTrivia(TriviaListener listener);
    void answerTrivia(int answerID, TriviaListener listener, int buttonClicked, int triviaID);
    void silentAnswerTrivia(int answer, TriviaListener listener, int triviaID);
}
