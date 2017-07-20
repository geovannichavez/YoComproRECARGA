package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

/**
 * Created by Josué Chávez on 17/07/2017.
 */

public interface ProfileView
{
    void loadViewsState(String fullName, String nickname, String photoUrl);
    void generateToast(String text);
    void showGenericDialog(DialogViewModel model);
}
