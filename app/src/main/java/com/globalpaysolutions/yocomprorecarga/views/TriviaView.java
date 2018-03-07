package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.QuestionTrivia;

/**
 * Created by Josué Chávez on 5/3/2018.
 */

public interface TriviaView
{
    void initialViewsState();
    void renderQuestion(QuestionTrivia trivia);
    void updateTimer(String remaining);
    void setViewsListeners();
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void showToast(String toast);
}
