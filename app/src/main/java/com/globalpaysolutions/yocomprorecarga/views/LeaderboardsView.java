package com.globalpaysolutions.yocomprorecarga.views;

import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.Leader;

import java.util.List;

/**
 * Created by Josué Chávez on 19/07/2017.
 */

public interface LeaderboardsView
{
    void initializeViews();
    void renderLeaderboardData(List<Leader> leaders);
    void setLastWinner(String data);
    void showLoadingMessage(String message);
    void hideLoadingMessage();
    void showGenericDialog(DialogViewModel dialogViewModel);
    void showGenericToast(String label);
    void swithTextColor(TextView textView);
}
