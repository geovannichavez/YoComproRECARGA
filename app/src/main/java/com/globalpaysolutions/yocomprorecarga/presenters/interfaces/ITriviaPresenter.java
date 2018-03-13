package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 5/3/2018.
 */

public interface ITriviaPresenter
{
    void initialize();
    void requestTrivia();
    void answerTrivia(int answerID, int buttonClicked, int triviaID, boolean answered);
    void finishTimer();
}
