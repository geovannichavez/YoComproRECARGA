package com.globalpaysolutions.yocomprorecarga.views;

import android.graphics.Bitmap;
import android.view.View;

import com.globalpaysolutions.yocomprorecarga.models.api.ListGameStoreResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;

import java.util.List;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public interface StoreView
{
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void setInitialValues(String currentCoins);
    void renderStoreItems(List<ListGameStoreResponse> items);
    void showSouvenirWonDialog(String souvenirName, String souvenirDescription, String url);
    void showNewAchievementDialog(String name, String level, String prize, String score, int resource);
    void createImageDialog(String title, String description, int resource);
}
