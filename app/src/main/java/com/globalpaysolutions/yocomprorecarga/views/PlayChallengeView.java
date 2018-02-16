package com.globalpaysolutions.yocomprorecarga.views;

import android.view.View;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

/**
 * Created by Josué Chávez on 9/2/2018.
 */

public interface PlayChallengeView
{
    void initializeViews();
    void setViewsListeners();
    void setIncomingChallenge();
    void showGenericDialog(DialogViewModel dialog, View.OnClickListener clickListener);
    void showLoadingDialog(String text);
    void hideLoadingDialog();
    void finishActivty();
    void highlightButton();

}
