package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public interface ChallengesView
{
    void initializeViews();
    void renderChallegenes();
    void showGenericDialog(DialogViewModel dialog);
    void showLoadingDialog(String string);
}
