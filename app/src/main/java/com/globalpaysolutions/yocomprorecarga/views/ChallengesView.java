package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.Challenge;

import java.util.List;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public interface ChallengesView
{
    void initializeViews();
    void renderChallegenes(List<Challenge> challenges);
    void showGenericDialog(DialogViewModel dialog);
    void showLoadingDialog(String string);
    void hideLoadingDialog();
}
