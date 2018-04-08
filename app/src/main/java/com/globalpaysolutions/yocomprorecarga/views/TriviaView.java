package com.globalpaysolutions.yocomprorecarga.views;

import android.view.View;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
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
    void showImageDialog(String title, String message, int resource, View.OnClickListener clickListener);
    void showSouvenirDialog(String name, String description, String url, View.OnClickListener clickListener);
    void navigateSouvenirs();
    void navigatePrizeDetail();
    void showGenericDialog(DialogViewModel dialog);
    void finishActivity();
    void removeClickable();
    void highlightButton(int buttonClicked, boolean correctAnswer);
    void highlightCorrect(int correctAnswerID);
}
