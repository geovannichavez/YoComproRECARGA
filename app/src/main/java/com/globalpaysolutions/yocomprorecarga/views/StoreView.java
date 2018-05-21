package com.globalpaysolutions.yocomprorecarga.views;

import android.view.View;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.ListGameStoreResponse;

import java.util.List;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public interface StoreView
{
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void setInitialValues(String currentCoins, String currentSouvenir);
    void renderStoreItems(List<ListGameStoreResponse> items);
    void showSouvenirWonDialog(String souvenirName, String souvenirDescription, String url);
    void showNewAchievementDialog(String name, String level, String prize, String score, int resource);
    void createImageDialog(String title, String description, int resource);
    void updateViews(String coinsLeft);
    void createGenericDialog(String title, String content);
    void showConfirmDialog(DialogViewModel dialogContent, int resource, View.OnClickListener onClickListener);
    void hideConfirmDialog();
}
