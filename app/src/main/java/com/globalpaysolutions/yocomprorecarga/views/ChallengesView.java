package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.ChallengeResultData;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.Challenge;

import java.util.List;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public interface ChallengesView
{
    void initializeViews(boolean locationVisible);
    void initializeValues(int totalWins,int totalLose,int totalDraw,int winLaPulga,int winElPibe,int winDinho,int winElComandante, int winZizou);
    void renderChallegenes(List<Challenge> challenges);
    void showGenericDialog(DialogViewModel dialog);
    void showLoadingDialog(String string);
    void hideLoadingDialog();
    void navigateChalengeResult(ChallengeResultData challengeResult);
}
