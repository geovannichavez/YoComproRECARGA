package com.globalpaysolutions.yocomprorecarga.views;

import android.view.View;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

import java.util.List;

/**
 * Created by Josué Chávez on 9/2/2018.
 */

public interface PlayChallengeView
{
    void initializeViews(List<String> betsValues, String rock, String papper, String scissors);
    void setViewsListeners();
    void setIncomingChallenge();
    void showGenericDialog(DialogViewModel dialog, View.OnClickListener clickListener);
    void showLoadingDialog(String text);
    void hideLoadingDialog();
    void finishActivty();
    void highlightButton();

}
