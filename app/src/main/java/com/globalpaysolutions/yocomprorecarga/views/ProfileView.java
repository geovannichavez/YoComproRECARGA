package com.globalpaysolutions.yocomprorecarga.views;

import android.content.Intent;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

/**
 * Created by Josué Chávez on 17/07/2017.
 */

public interface ProfileView
{
    void loadViewsState(String countryUrl, String nickname, String photoUrl);
    void generateToast(String text);
    void showGenericDialog(DialogViewModel model);
    void updateIndicators(String totalCoins, String totalSouvenirs);
    void navigateSouvenirs(Intent souvenirs);
    void loadCountryBadge(String worldcupCountryUrl);
}
